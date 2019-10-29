/**
 * Created by zhouyi on 2019/10/19.
 */
(function($,jsPlumb) {

    if(!jsPlumb){
        return;
    }

    var _CLICK_CLASS = 'ui-click',
        _SELECTED_CLASS='ui-selected',
        _overpanelTopHeight = 28,//浮动面板上高
        _overpanelRightWidth = 27;//浮动面板右宽;
    /**
     * 基于jsPlumb的流程图
     */
    $.widget("youi.plumbFlow",$.youi.abstractWidget,$.extend({},$.youi.command,{

        options:{
            delay: 100,//
            bindResize:true,
            distance: 1,
            nodeHtml:function (node) {
                return node.text||'';
            },
            overpanels:[{name:'addRefNode',text:'',subname:'refNode'},{name:'startSequence',text:''},{name:'removeNode',text:''}],
            cancel: "input,textarea,button,select,option,[contenteditable=true],.content-editable"
        },
        /**
         *
         * @private
         */
        _initWidget:function () {
            this._mouseInit();
            this._bindWidgetCommand();
            this._initOverPanels();
            this.scale = 1;
            this.plumb = jsPlumb.getInstance({
                PaintStyle: { strokeWidth: 1 },
                HoverPaintStyle:{strokeWidth: 2,stroke:'red'},
                Container:this.element,
                Anchors: [["Left","Right","Bottom"], ["Left","Right","Top","Bottom"]]
            });

            // this.plumb.importDefaults({
            //     ConnectionsDetachable: false
            // });
            this.plumb.bind("click", this._proxy('_connectionClick'));


            var node01 = this._addNode({text:'制造强国产业数据'});
            this._addConnector(node01,this._addNode({text:'宏观经济'}));
            this._addConnector(node01,this._addNode({text:'生产力布局'}));

            this._resize();
        },

        _initOverPanels:function(){
            this.overpanelsElement = this.element.find('>.over-panels');
            if(this.overpanelsElement.length==0){

                this.overpanelsElement = $('<div class="over-panels"></div>').appendTo(this.element);

                var htmls = [];

                if(this.options.overpanels){
                    for(var i=0;i<this.options.overpanels.length;i++){
                        htmls.push(this._buildOverItemHtml(this.options.overpanels[i],i));
                    }
                }

                this.overpanelsElement.html(htmls.join(''));
            }
        },

        _buildOverItemHtml:function(overItem,index){
            var htmls = [],
                styles = ['over-item'],
                datas = {};

            if(!overItem.name){
                return '';
            }

            styles.push(overItem.name);
            datas.name = overItem.name;
            datas.index = index;

            if(overItem.subname){
                styles.push(overItem.subname);
                datas.subname = overItem.subname;
            }

            htmls.push('<span title="'+(overItem.caption||'')+'" class="'+styles.join(' ')+'"');

            for(var i in datas){
                htmls.push(' data-'+i+'='+datas[i]+' ');
            }

            htmls.push('></span>');

            return htmls.join('');
        },

        /**
         *
         * @private
         */
        _initAction:function () {
            this._on({
                'click':function (event) {
                    this.overpanelsElement.hide();
                },
                'click .node-text':function (event) {
                    var textElem = $(event.currentTarget).attr('contenteditable',true);
                    textElem.focus();
                },
                'mouseleave .node-text':function (event) {
                    var textElem = $(event.currentTarget);
                    textElem.html(textElem.text()).removeAttr('contenteditable');
                    return false;
                },
                'click .node':function (event) {
                    this._clickNode($(event.currentTarget),event.shiftKey);
                },'mouseenter .node':function(event){
                    var nodeElement = $(event.currentTarget);
                    this._showOverpanels(nodeElement);
                },'mouseleave .over-panels':function(event){
                    this.overpanelsElement.hide();
                },'mousedown .over-item':function(event){
                    var item = $(event.currentTarget);//浮动菜单鼠标点击
                    this.execCommand(item,item.data());
                    this.overpanelsElement.hide();
                }
            });
        },
        /**
         * 显示浮动面板
         */
        _showOverpanels:function(element){
            //
            var left = element[0].offsetLeft,
                top =  element[0].offsetTop,
                paddingLeft = Math.max(element.outerWidth(),60),
                overpanels = this.options.overpanels;

            var elemGroups = element[0].className.split(' ');

            var topCount = 0,maxTopCount=3,
                centerCount = 0,maxCenterCount=2,
                bottomCount = 0,maxBottomCount=3;
            //
            this.overpanelsElement.find('.over-item').each(function(){
                var overItemElement = $(this),
                    data = overItemElement.data(),
                    index = data.index,
                    overItem = overpanels[index];

                var show = true;
                if(overItem.excludes){	//overItem.excludes 排除的分组
                    for(var i=0;i<elemGroups.length;i++){
                        if($.inArray(elemGroups[i],overItem.excludes)!=-1){
                            show = false;
                            break;
                        }
                    }
                }

                if(show){
                    for(var i=0;i<elemGroups.length;i++){
                        if($.inArray(elemGroups[i],overItem.excludes)!=-1){
                            show = true;
                            break;
                        }
                    }
                }

                overItemElement
                    .removeClass('over-top over-top-first over-top-last over-center over-bottom over-bottom-first over-bottom-last')
                if(show){
                    //添加节点到位置  下，上，
                    if(topCount<maxTopCount){
                        topCount++;
                        _addIndexElementClass(overItemElement,topCount,maxTopCount,'over-top');
                    }else if(centerCount<maxCenterCount){
                        centerCount++;
                        _addIndexElementClass(overItemElement,centerCount,maxCenterCount,'over-center');
                    }else if(bottomCount<maxBottomCount){
                        bottomCount++;
                        _addIndexElementClass(overItemElement,bottomCount,maxBottomCount,'over-bottom');
                    }
                }
            });

            this.overpanelsElement.css({
                left:left,
                top:top-_overpanelTopHeight-1,
                width:paddingLeft+_overpanelRightWidth
            }).show();
            //当前悬浮对应的节点
            this.overNodeElement = element;
        },
        /**
         *
         * @param nodeElem
         * @private
         */
        _clickNode:function (nodeElem,shiftKey) {
            var lastClickNode = this.element.find('.node.'+_CLICK_CLASS);
            lastClickNode.removeClass(_CLICK_CLASS).removeClass(_SELECTED_CLASS);
            nodeElem.addClass(_CLICK_CLASS).addClass(_SELECTED_CLASS);

            if(shiftKey && lastClickNode.length){
                this._addConnector(lastClickNode,nodeElem);
            }
        },

        /**
         *
         * @param event
         * @private
         */
        _mouseStart:function (event) {
            var dragElement = $(event.target);
            if(this.startSequenceNode){
                //
            }else if(dragElement.hasClass('.node')){
                this.dragElem = dragElement;
            }else{
                this.selectionHelper = this.element.find('.selection-helper');
                this.selectees = $('.node,.transition,.point', this.element[0]);
                var parentOffset = this.element.offset();
                this.selectees.each(function() {
                    var $this = $(this),
                        pos = $this.offset();
                    $.data(this, "selectable-item", {
                        element: this,
                        $element: $this,
                        left: this.offsetLeft,
                        top: this.offsetTop + parentOffset.top,
                        right: this.offsetLeft + $this.outerWidth(),
                        bottom: this.offsetTop + $this.outerHeight(),
                        startselected: false,
                        selected: $this.hasClass("ui-selected"),
                        selecting: $this.hasClass("ui-selecting"),
                        unselecting: $this.hasClass("ui-unselecting")
                    });
                });

                this.selectees.filter(".ui-selected").each(function() {
                    var selectee = $.data(this, "selectable-item");
                    selectee.startselected = true;
                    if (!event.metaKey && !event.ctrlKey) {
                        selectee.$element.removeClass("ui-selected");
                        selectee.selected = false;
                        selectee.$element.addClass("ui-unselecting");
                        selectee.unselecting = true;
                    }
                });

                if(this.selectionHelper.length==0){
                    this.selectionHelper = $('<div class="selection-helper"></div>').appendTo(this.element);
                }

                this.selectionHelper.addClass('selecting');
                this.selectionStartPos = {left:event.pageX,top:event.pageY};
            }
        },
        _mouseDrag: function(event) {
            if(this.startSequenceNode){
                this._showSequenceTrace(event);
            }else if(this.selectionHelper){//区域选择
                //计算位置
                var pos = {
                    left:Math.min(event.pageX,this.selectionStartPos.left) - this.element.offset().left+this.element[0].scrollLeft,
                    top:Math.min(event.pageY,this.selectionStartPos.top)- this.element.offset().top+this.element[0].scrollTop,
                    width:Math.abs(event.pageX-this.selectionStartPos.left),
                    height:Math.abs(event.pageY-this.selectionStartPos.top)
                };

                this.selectionHelper.css(pos);

                this.selectees.each(function(){
                    var selectee = $.data(this, "selectable-item"),
                        hit = false;

                    hit = (selectee.left > pos.left && selectee.right < pos.left+pos.width && selectee.top > pos.top && selectee.bottom < pos.top+pos.height);

                    if (hit) {
                        // SELECT
                        if (selectee.selected) {
                            selectee.$element.removeClass("ui-selected");
                            selectee.selected = false;
                        }
                        if (selectee.unselecting) {
                            selectee.$element.removeClass("ui-unselecting");
                            selectee.unselecting = false;
                        }
                        if (!selectee.selecting) {
                            selectee.$element.addClass("ui-selecting");
                            selectee.selecting = true;
                        }
                    } else {
                        // UNSELECT
                        if (selectee.selecting) {
                            if ((event.metaKey || event.ctrlKey) && selectee.startselected) {
                                selectee.$element.removeClass("ui-selecting");
                                selectee.selecting = false;
                                selectee.$element.addClass("ui-selected");
                                selectee.selected = true;
                            } else {
                                selectee.$element.removeClass("ui-selecting");
                                selectee.selecting = false;
                                if (selectee.startselected) {
                                    selectee.$element.addClass("ui-unselecting");
                                    selectee.unselecting = true;
                                }
                            }
                        }
                        if (selectee.selected) {
                            if (!event.metaKey && !event.ctrlKey && !selectee.startselected) {
                                selectee.$element.removeClass("ui-selected");
                                selectee.selected = false;

                                selectee.$element.addClass("ui-unselecting");
                                selectee.unselecting = true;
                            }
                        }
                    }
                });
            }
        },

        _mouseStop: function(event) {
            if(this.startSequenceNode){//连线
                this.startSequenceNode = null;
            }else if(this.selectionHelper){
                $(".ui-unselecting", this.element[0]).each(function() {
                    var selectee = $.data(this, "selectable-item");
                    selectee.$element.removeClass("ui-unselecting");
                    selectee.unselecting = false;
                    selectee.startselected = false;
                });

                $(".ui-selecting", this.element[0]).each(function() {
                    var selectee = $.data(this, "selectable-item");
                    selectee.$element.removeClass("ui-selecting").addClass("ui-selected");
                    selectee.selecting = false;
                    selectee.selected = true;
                    selectee.startselected = true;
                });
                //清理上次选择
                this.plumb.removeFromAllPosses(this.element.find('.node'));
                //
                var selected = this.element.find('.ui-selected');
                if(selected.length){
                    this.plumb.addToPosse(selected, "posse");
                }

                this.selectionHelper.removeClass('selecting');
                this.selectionHelper = null;
                this.selectionStartPos = null;
                this.selectees = null;
            }

            this.currentDrag = null;

            this.helper&&this.helper.remove();
            this.helper = null;

        },

        _showSequenceTrace:function(event){
            if(this.startSequenceNode){

            }
        },
        /**
         *
         * @private
         */
        _connectionClick:function (info) {

        },

        /**
         * 添加节点
         * @private
         */
        _addNode:function (node,subname,x,y) {
            var htmls = [], x = x ||30,y = y||30;

            htmls.push('<div style="left:'+x+'px;top:'+y+'px;" class="node"><span class="node-text">'+(node.text||'')+'</span></div></div>');

            this.element.append(htmls.join(''));

            var nodeElem = this.element.find('.node:last');

            var plumb = this.plumb;
            nodeElem.resizable({
                resize: function (event, ui) {
                    //plumb.revalidate(ui.helper)
                }
            });
            //
            this.plumb.draggable(nodeElem);
            return nodeElem;
        },
        /**
         *
         * @private
         */
        _addConnector:function (source,target) {
            this.plumb.connect({
                source: source,
                target: target,
                connector: [ "Flowchart",
                    {
                        cornerRadius: 3,
                        stub:16
                    }
                ],
                //endpoint: ["Dot", {radius: 8}],
                endpoint: 'Blank',
                paintStyle: { stroke: 'rgb(132, 172, 179)', strokeWidth: 3 },
                endpointStyle: { fill: 'rgb(132, 172, 179)', outlineStroke: 'darkgray', outlineWidth: 2 },
                overlays: [ ['Arrow', { width: 12, length: 12, location: 1 }]]
            });
        },

        _zoom:function () {
            var scaleStyle = 'scale('+this.scale+')';
            this.element.css({
                "-webkit-transform": scaleStyle,
                "-moz-transform": scaleStyle,
                "-ms-transform": scaleStyle,
                "-o-transform": scaleStyle,
                "transform": scaleStyle
            })
            this.plumb.setZoom(this.scale );

            this.plumb.repaintEverything();
        },

        /**
         * 创建节点
         * @param dom
         * @param commandOptions
         */
        createNode:function(dom,commandOptions){
            this._addNode({text:'新节点'});
        },

        zoomReset:function (dom,commandOptions) {
            this.scale = 1;
            this._zoom();
        },

        /**
         * 放大
         * @param dom
         * @param commandOptions
         */
        zoomIn:function (dom,commandOptions) {
            if(this.scale>3){
                return;
            }
            this.scale *= 1.1;
            this._zoom();
        },

        /**
         * 缩小
         * @param dom
         * @param commandOptions
         */
        zoomOut:function (dom,commandOptions) {
            if(this.scale<0.3){
                return;
            }
            this.scale *= 0.9;
            this._zoom();
        },

        /**
         * 启动画线
         */
        startSequence:function(dom,commandOptions){
            if(this.overNodeElement){
                this.startSequenceNode = this.overNodeElement;
            }
        },

        addRefNode:function (dom,commandOptions) {
            //
            if(this.overNodeElement&&commandOptions.subname){
                var deltLeft = 100 + (Math.random()*1000)%100,
                    x = this.overNodeElement[0].offsetLeft+deltLeft,
                    y = this.overNodeElement[0].offsetTop;
                var refNode = this._addNode({}, commandOptions.subname, x, y);
                this._addConnector(this.overNodeElement,refNode);
            }
        },

        /**
         * 删除连接
         * @param dom
         * @param commandOptions
         */
        removeNode:function (dom,commandOptions) {
            if(this.overNodeElement){
                this.plumb.deleteConnectionsForElement(this.overNodeElement);
                this.overNodeElement.remove();
                this.overNodeElement = null;
            }
        },

        /**
         *
         */
        getXml:function () {
            var xmls = ['<?xml version="1.0" encoding="UTF-8"?>'],
                skipProps = ['id','key','selectableItem'];

            xmls.push('<flow>');

            this.element.find('.node').each(function(){
                var elem = $(this),
                    datas = elem.data();
                xmls.push('<node id="'+elem.attr('id')+'" ');

                for(var property in datas){
                    if($.inArray(property,skipProps)!=-1){
                        continue;
                    }
                    var propertySplit = property.split(":");
                    if(propertySplit.length>1){
                        xmls.push(propertySplit[1]+'="'+datas[property]+'" ');
                    }else{
                        xmls.push('data-'+$.youi.stringUtils.convertDataProperty(property,'-')+'="'+datas[property]+'" ');
                    }
                }

                xmls.push(' left="'+this.offsetLeft+'" ');
                xmls.push(' top="'+this.offsetTop+'" ');
                xmls.push(' width="'+(this.offsetWidth-2)+'" ');
                xmls.push(' height="'+(this.offsetHeight-2)+'" ');

                xmls.push('><![CDATA[');
                xmls.push(elem.html());
                xmls.push(']]></node>');
            });

            xmls.push('</flow>');

            var connections = this.plumb.getConnections();

            $(connections).each(function () {
                xmls.push('<transition id="'+this.id+'" ');
                xmls.push(' from="'+this.sourceId+'" ');
                xmls.push(' to="'+this.targetId+'" ');
                xmls.push(' caption="" ');
                xmls.push('>');
                xmls.push('</transition>');
            });
            xmls.push('</flow>');

            return xmls.join('');
        },

        _resize:function () {
            //this._resizePageHeight();
        }

    }));

    function _addIndexElementClass(element,index,maxCount,className){
        element.addClass(className);

        if(index==1){
            element.addClass(className+'-first');
        }

        if(index==maxCount){
            element.addClass(className+'-last');
        }
    }
})(jQuery,jsPlumb);