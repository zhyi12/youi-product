/*!
 * youi JavaScript Library v3.0.0
 *
 *
 * Copyright 2016-2020, zhyi_12
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Date: 2019-11-21
 */
(function($,d3,topojson) {

    if(!d3){//基于d3实现的地图组件
        console.log('d3 is missing');
        return;
    }

    //边界颜色
    var chinaAreaId = '000000',
        stk_clr_hai = "#4190da",//006fbd
        stk_clr_sekuai_quanguo = "white"; //全国图边界为粉色#e6bad6

    //字体颜色
    var txt_clr_linsheng_linshi = "#5e6060",
        txt_clr_linguo = "black",
        txt_clr_hai = "#006fbd",
        txt_clr_zhou_meng_zizhiqu = "red";

    var bar_clr = '#006fbd',
        barWidth = 12,tooltipWidth = 30,tooltipHeight=50;

    var cirRadius = 4,
        cirRadiusBigger = cirRadius + 1.4,
        textSize = 11; //点注记、线注记字体大小
    /**
     * 地图组件
     */
    $.widget("youi.geomap",$.youi.abstractWidget,$.extend({}, {

        options: {
            bindResize:true,
            width : 930,
            height : 750,
            areaId:chinaAreaId,//默认全国地图
            src:'../datas/{areaId}.json',//地图信息
            lineSrc:'../datas/{areaId}_Line.geojson'//线条
        },

        /**
         * 初始化html内容，在_initWidget前执行
         * @private
         */
        _defaultHtmls:function () {
            this.contentElem = this.element.find('>.'+this.widgetName+'-content').css('overflow','hidden');
            var htmls = ['<svg></svg>'];
            this.contentElem.html(htmls.join(''));
        },
        /**
         * 组件初始化
         * @private
         */
        _initWidget:function () {
            this._createSvgGroup();//创建svg的顶级g标签

            this.projection = d3.geoMercator()
                .center([104.2, 32])      //设置地图中心位置，前经度后纬度  11945937, 4090836
                .scale(550 / 766 * this.options.width) //map宽766时，对应缩放比例700比较合适
                .translate([this.options.width / 2, this.options.height / 2])//设置平移量
                .precision(0) //拒绝插值;
            this.geoPath = d3.geoPath().projection(this.projection);

            this.drawCompleted = false;//标识是否已经完成地图基本信息的绘制
            this.loadGeoMap();
            this._resize();
        },

        _initAction:function(){
            this._on({
                'click':function (event) {
                    this._hideBarTooltip();
                }
            });
        },

        /**
         *
         * @private
         */
        _createSvgGroup:function(){
            var svgGroup = d3.select(this.contentElem.find('>svg:first')[0])
                .attr('width',this.options.width)
                .attr('height',this.options.height).call(d3.zoom().on("zoom", function () {
                    svgGroup.attr("transform", d3.event.transform);
                })).append('g');

            this.landGroup = svgGroup.append('g').attr('id','base');//基础地图图层
            this.borderlineGroup = svgGroup.append('g').attr('id','line');//边界线条图层
            this.textGroup = svgGroup.append('g').attr('id','text');//文本信息
            this.barGroup = svgGroup.append('g').attr('id','bar');//柱状图
            this.tooltipGroup= svgGroup.append('g').attr('id','tooltip').style('display','none');
            //
            this.tooltipGroup.append('rect').attr('width',tooltipWidth).attr('height',tooltipHeight)
                .style('fill','white').attr('stroke','#ddd').attr('rx',10).attr('ry',10);//tooltip
            this.tooltipGroup.append('text').style('font-size','14px');
        },

        /**
         * 加载地图数据
         */
        loadGeoMap:function () {
            var src = $.youi.recordUtils.replaceByRecord(this.options.src,{areaId:this.options.areaId});

            if(chinaAreaId == this.options.areaId){//全国地图
                d3.json(src,{}).then(this._proxy('_parseGeoMap'));
            }else{
                d3.json(src,{}).then(this._proxy('_parseGeoPartMap'));//省、市地图
            }
        },

        /**
         *
         * @private
         */
        _parseGeoMap:function (response) {
            var land = topojson.feature(response,response.objects[this.options.areaId]);

            this._drawLand(land.features);

            var lineSrc = $.youi.recordUtils.replaceByRecord(this.options.lineSrc,{areaId:this.options.areaId});
            d3.json(lineSrc,{}).then(this._proxy('_parseGeoLine'));
        },

        /**
         *
         * @private
         */
        _parseGeoPartMap:function(response){
            var areaId = this.options.areaId;
            var land = topojson.feature(response,response.objects[this.options.areaId]);

            var landAreas = land.features.filter(function (d) {

                if(areaId==d.properties.QUHUADAIMA){
                    return false;
                }
                return  true;

            });

            this.projection.scale(1);//初始scale设置为1

            var center = _getCenters(landAreas);//计算中心点
            var zoomScale = _getZoomScale(landAreas, this.options.width, this.options.height, this.projection);//计算缩放比例

            //重设投影
            this.projection = d3.geoMercator().center(center)
                .scale(zoomScale).translate([this.options.width / 2, this.options.height / 2]).precision(0);
            this.geoPath = d3.geoPath().projection(this.projection);

            //区划区块
            this._drawLand(landAreas);
            //下级行政区划名称
            //this._drawGeoText()

            var lineSrc = $.youi.recordUtils.replaceByRecord(this.options.lineSrc,{areaId:this.options.areaId});
            d3.json(lineSrc.replace('_Line','_Point'),{}).then(this._proxy('_drawPointAndText'));

        },

        /**
         * 点、文本
         * @private
         */
        _drawPointAndText:function(response){

            var projection = this.projection;

            this.textGroup.selectAll("text")
                .data(response.features)
                .enter()
                .append("text")
                .attr("id","text")
                .text(function (d) { return d.properties.Name != null ? d.properties.Name : d.properties.NAME })
                .attr("x", function (d) {
                    var x = projection(d.geometry.coordinates)[0].toString();
                    return x - 10;
                })
                .attr("dx", function (d) {
                    return getTextDx(d.properties.Location, 1, 5).toString();
                })
                .attr("y", function (d) {
                    var y = projection(d.geometry.coordinates)[1];

                    return y.toString();
                    //return getTextPos_y(d.properties.Location, projection(d.geometry.coordinates)[1],1)
                }).attr("dy", function (d) {
                    return getTextDy(d.properties.Location, 1, 5).toString();
                }).style('font-size',textSize+'px');

        },

        _drawLand:function(landAreas){
            this.landGroup.selectAll('path')
                .data(landAreas)
                .enter()
                .append('path')
                .attr('id',function (d,i) {
                    return d.properties.QUHUADAIMA;
                })
                .attr('stroke', '#ddd')
                .attr('stroke-width', 1)
                .attr('fill', function(d, i) {
                    return parseAreaColor(d);
                })
                .attr('d', this.geoPath)
                .on('mouseover', function(d, i) {
                    if(d.properties.FillColor){
                        d3.select(this).attr('fill', 'yellow')
                    }
                })
                .on('mouseout', function(d, i) {
                    d3.select(this).attr('fill', parseAreaColor(d));
                });
        },

        /**
         *
         * @param response
         * @private
         */
        _parseGeoLine:function (response) {
            this._drawGeoText(response);
            this._drawGeoBorderLine(response);
            //完成绘制
            this.drawCompleted = true;
            //test
            this.draw3DBar();
        },

        /**
         *
         * @param response
         * @private
         */
        _drawGeoText:function(response){
            var textValues = response.features.filter(function (d) {
                if (d.properties.QUHUADAIMA == undefined) return false;
                var len = d.properties.QUHUADAIMA.length;
                if (len == 4 || len == 5 || len == 6 || len == 7) return true;
            });
            //
            this.textGroup.selectAll("g")
                .data(textValues)
                .enter()
                .append("g")
                .style('fill', function (d) {
                    if (d.properties.QUHUADAIMA == undefined) return "none";

                    if (d.properties.QUHUADAIMA.length == 2) {
                        return txt_clr_hai
                    } else if (d.properties.QUHUADAIMA.length == 6) {
                        return txt_clr_linguo;
                    } else if (d.properties.QUHUADAIMA.length == 7) {
                        return txt_clr_zhou_meng_zizhiqu;
                    } //洲、盟、地区
                    else {
                        return txt_clr_linsheng_linshi;
                    }
                })
                .selectAll("text").data(this._proxy('_processAreaDataText'))
                .enter()
                .append("text")
                .text(function (d) {
                    return d.text;
                }).style("font-size", function (d) {
                return "9px";
            })
                .attr("x", function (d) {
                    return d.coordinates[0].toString();
                }).attr("y", function (d) {
                return d.coordinates[1].toString();
            });
        },
        /**
         *
         * @param d
         * @param i
         * @returns {Array}
         * @private
         */
        _processAreaDataText:function(d,i){
            var data = [];
            var name = d.properties.Name != null ? d.properties.Name : d.properties.NAME
            var qhdm = d.properties.QUHUADAIMA != null ? d.properties.QUHUADAIMA : ""
            for (var j = 0 ; j < d.geometry.coordinates.length; j++) {
                var n = { "text": name.substr(j, 1), "coordinates": this.projection(d.geometry.coordinates[j]), "qhdm": qhdm }
                data.push(n);
            }
            return data;
        },

        /**
         *
         * @param response
         * @private
         */
        _drawGeoBorderLine: function (response) {
            //画国界线
            var borderlinePath = response.features.filter(function (d) {
                if (d.properties.QUHUADAIMA == undefined) return false;
                var len = d.properties.QUHUADAIMA.length;
                if (len != 4 && len != 5 && len != 6 && len != 7) return true;
            });
            //国界线
            this.borderlineGroup.selectAll("path")
                .data(borderlinePath)
                .enter()
                .append("path").attr("stroke", function (d) {
                var guojieClr = "gray";
                var anjiaoClr = "gray";
                var len = d.properties.QUHUADAIMA.length;
                if (len == 8) {
                    return "white"
                } else if (len == 9) {
                    return stk_clr_hai;
                } else if (len == 11 || len == 12) {
                    return stk_clr_sekuai_quanguo;
                } else if (len == 10) {
                    return guojieClr;
                } else if (len == 3) {
                    return anjiaoClr;
                } else {
                    return "black"
                }
            }).attr("fill", "none").attr("stroke-width", function (d) {
                var len = d.properties.QUHUADAIMA.length;
                if (len == 8) {
                    return "2"
                } else if (len == 9) {
                    return "1";
                } else {
                    return "1";
                }
            }).attr("vector-effect", "non-scaling-stroke") //边框不缩放
            .attr("d", this.geoPath)
            .attr("stroke-dasharray", function (d) {
                var len = d.properties.QUHUADAIMA.length;
                if (len == 11) { //海南省广东省界线
                    return "6,6";
                } else if (len == 12) {//香港省界
                    return "2,4,6,4";
                }
            });
        },

        /**
         * 在地图上绘制3D柱状图
         */
        draw3DBar:function (datas) {
            datas = datas||{'110000':200,'120000':300,'500000':500,'420000':430};

            if(this.drawCompleted){//
                var textValues = [];
                //获取行政区划数据
                this.textGroup.selectAll("g").datum(function (d,i) {
                    if(d.properties.QUHUADAIMA.match(/[0-9]{7}/)) {
                        var areaId = d.properties.QUHUADAIMA.substr(0, 6),
                            value = datas[areaId];
                        if (value || value == 0) {
                            textValues.push(d);
                        }
                    }
                });
                //绘制行政区划柱状图数据
                this.barGroup.selectAll("g").data(textValues)
                    .enter()
                    .append('g')
                    .datum(this._proxy('_processTextGroup',datas))
                    .on('mouseover',this._proxy('_mouseOverBar',datas))
                    .on('mouseout',this._proxy('_mouseOutBar',datas));
            }
        },

        _mouseOverBar:function(d,i,list,datas){
            var barElem = d3.select(list[i]);
            barElem.select('rect').style('fill', 'yellow');
            barElem.selectAll('path').style('fill', 'yellow');

            this._showBarTooltip(d);
        },

        _mouseOutBar:function(d,i,list,datas){
            var barElem = d3.select(list[i]);
            barElem.select('rect').style('fill', bar_clr);
            barElem.selectAll('path').style('fill', bar_clr);
            this._hideBarTooltip();
        },

        /**
         * 显示tooltips
         * @param d
         * @private
         */
        _showBarTooltip:function(d){
            var x = this.projection(d.geometry.coordinates[0])[0].toString()-0,
                y = this.projection(d.geometry.coordinates[0])[1].toString()-0;

            var left = x - tooltipWidth/2+barWidth/2,top = y - d.chartData.barHeight - tooltipHeight - 8;
            var textElem = this.tooltipGroup.select('text').attr('x',left+5).attr('y',top+16).text(d.properties.NAME);
            textElem.append('tspan').attr('x',left+5).attr('y',top+16+22).text(d.chartData.value);

            var textWidth = textElem.node().getComputedTextLength();
            //刷新边框
            this.tooltipGroup.style('display','block')
                .select('rect').attr('x',left).attr('y',top).attr('width',textWidth+10);
        },

        /**
         * 隐藏tooltips
         * @private
         */
        _hideBarTooltip:function(){
            this.tooltipGroup.style('display','none');
        },

        /**
         *
         * @param d
         * @param i
         * @returns {*}
         * @private
         */
        _processTextGroup:function (d,i,list,datas) {
            if(d && d.properties.QUHUADAIMA.match(/[0-9]{7}/)){
                var areaId = d.properties.QUHUADAIMA.substr(0,6),
                    value = datas[areaId];

                if(value || value == 0 ){
                    var x = this.projection(d.geometry.coordinates[0])[0].toString()-0,
                        y = this.projection(d.geometry.coordinates[0])[1].toString()-0,
                        ratio = 0.2,
                        barHeight = datas[areaId]*ratio;//比例
                    //创建3D柱状图
                    d.chartData = {value:value,barHeight:barHeight};//记录bar的top坐标
                    _create3DBar(d3.select(list[i]).append('g'),x,y-barHeight,barWidth,barHeight);
                }
                return d;
            }
            return {};
        },

        /**
         *
         * @private
         */
        _resize:function () {
            var parentHeight =
                    Math.min(this.element.offsetParent().height(),$(window).height()),
                contentHeight = parentHeight;
            this.contentElem.height(contentHeight);
        }
    }));

    /**
     * 获取区域颜色
     * @param d
     * @returns {string|*}
     */
    function parseAreaColor(d) {
        if(d.properties && d.properties.FillColor){
            return  d.properties.FillColor;
        }
        return 'transparent';
    }

    /**
     *
     * @param container
     * @private
     */
    function _create3DBar(container,x,y,barWidth,barHeight) {
        var color = bar_clr;
        container.append('rect')
            .attr('width',barWidth)
            .attr('height',barHeight)
            .attr("x", function (d) {
                return x;
            }).attr("y", function (d) {
                return y;
            }).style('fill',color);//初始颜色
        //立方体顶面
        container.append('path')
            .attr('d',function (d) {
                return bar3DCubeTop({x:x,y:y,width:barWidth,height:barHeight});
            }).style('fill',color);

        //立方体侧面
        container
            .append('path')
            .attr('d',function (d) {
                return bar3DCubeSide({x:x,y:y,width:barWidth,height:barHeight});
            }).style('fill',color);
    }

    var angleRate = 7,widthRate = 0.4;
    /**
     *
     *立方体侧面
     */
    function bar3DCubeSide(rect) {
        var path = d3.path();
        var angle = Math.PI/angleRate;

        var deltX = rect.width*widthRate,
            deltY = deltX/Math.cos(angle) ;

        path.moveTo(rect.x+deltX+rect.width, rect.y-deltY);
        path.lineTo(rect.x+deltX+rect.width, rect.y-deltY+rect.height);
        path.lineTo(rect.x+rect.width, rect.y+rect.height);
        path.lineTo(rect.x+rect.width, rect.y);
        path.closePath();
        return path.toString();
    }

    /**
     * 立方体顶面
     */
    function bar3DCubeTop(rect) {
        var path = d3.path();
        var angle = Math.PI/angleRate;

        var deltX = rect.width*widthRate,
            deltY = deltX/Math.cos(angle) ;

        path.moveTo(rect.x,rect.y);
        path.lineTo(rect.x+deltX, rect.y-deltY);
        path.lineTo(rect.x+deltX+rect.width, rect.y-deltY);
        path.lineTo(rect.x+rect.width, rect.y);

        path.closePath();
        return path.toString();
    }


    //获得地图的中心
    function _getCenters(features) {
        var longitudeMin = 100000;
        var latitudeMin = 100000;
        var longitudeMax = 0;
        var latitudeMax = 0;

        features.forEach(function (e) {
            var a = d3.geoBounds(e);
            if (a[0][0] > 0) {
                if (a[0][0] < longitudeMin) {
                    longitudeMin = a[0][0];
                }
                if (a[0][1] < latitudeMin) {
                    latitudeMin = a[0][1];
                }
                if (a[1][0] > longitudeMax) {
                    longitudeMax = a[1][0];
                }
                if (a[1][1] > latitudeMax) {
                    latitudeMax = a[1][1];
                }
            }
        });

        var a = (longitudeMax + longitudeMin) / 2;
        var b = (latitudeMax + latitudeMin) / 2;

        return [a, b];
    }

//设置地图的大小
    function _getZoomScale(features, width, height, projection) {
        var longitudeMin = 30000000;
        var latitudeMin = 30000000;
        var longitudeMax = 0;
        var latitudeMax = 0;
        features.forEach(function (e) {
            var a = d3.geoBounds(e);
            if (a[0][0] > 0) {
                if (a[0][0] < longitudeMin) {
                    longitudeMin = a[0][0];
                }
                if (a[0][1] < latitudeMin) {
                    latitudeMin = a[0][1];
                }
                if (a[1][0] > longitudeMax) {
                    longitudeMax = a[1][0];
                }
                if (a[1][1] > latitudeMax) {
                    latitudeMax = a[1][1];
                }
            }
        });
        var pointMin = projection([longitudeMin, latitudeMin]);
        var pointMax = projection([longitudeMax, latitudeMax]);
        var pad = 20; //留边
        var zoom = Math.min((width-pad) / Math.abs(pointMin[0] - pointMax[0]), (height-pad) / Math.abs(pointMin[1] - pointMax[1]));
        return zoom
    }


    function getTextDx(loc, scale, type) {
        var radius = cirRadius;

        var padding = 2; //空隙

        var dx = 0
        if (loc == 1 || loc == 4 || loc == 7) {
            dx = -(radius + padding) / scale;
        } else if (loc == 3 || loc == 6 || loc == 9) {
            dx = (radius + padding) / scale;
        }
        return dx;
    }


    function getTextDy(loc, scale,type) {
        var radius = cirRadius;

        var padding = 2; //空隙

        var tSize = textSize;

        var dy = 0;
        if (loc == 1 || loc == 2 || loc == 3) {
            dy = -(radius + padding) / scale;
        } else if (loc == 4 || loc == 5 || loc == 6) {
            dy = tSize * 0.3 / scale; //文字相对锚点居中
        } else if (loc == 7 || loc == 8 || loc == 9) {
            dy = (radius + padding) / scale;
            dy = dy + tSize * 0.6 / scale; //文字相对锚点下移一行
        }
        return dy;
    }


})(jQuery,d3,topojson);