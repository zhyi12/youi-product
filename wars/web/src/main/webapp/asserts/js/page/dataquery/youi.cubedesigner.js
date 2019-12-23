/**
 * Created by zhouyi on 2019/9/2.
 */
(function($) {

    var _COMMAND = "cubeDesignerCommand",
        _AGGREGATES = ['sum','count','avg','max','min'];

    $.widget("youi.cubeDesigner", $.youi.abstractWidget, $.extend({}, $.youi.abstractDesigner,{
        /**
         *
         */
        options:{
            bindResize:true,
            initHtml:true
        },
        /**
         *
         * @private
         */
        _initWidget:function () {
            this.element.addClass('page-inner-height');

            this._initRefWidget();//初始化关联组件
            this._initContentHtml();
            this._bindWidgetCommand();
            //初始化交叉表
            this.crossTableElem = this.contentElem.find('>.crossTable').crossTable({
                afterParse:this._proxy('_afterParseCubes')
            });
            this._resize();
        },
        /**
         *
         * @param ui
         * @private
         */
        _afterParseCubes:function(event,ui){
            var groups = ['cube-editor'];
            if(ui.canTransposition){
                groups.push('transposition');
            }
            this.element.trigger('activeTools',{groups:groups})
        },

        _initContentHtml:function(){
            this._initContent();
            var htmls = [];
            htmls.push('<div class="cube-query">');
            htmls.push( _buildCubeQueryRowHtml({id:'row',text:'行标签',icon:'list'}));
            htmls.push( _buildCubeQueryRowHtml({id:'col',text:'列标签',icon:'columns'}));
            htmls.push( _buildCubeQueryRowHtml({id:'measure',text:'值',icon:'table'}));
            htmls.push( _buildCubeQueryRowHtml({id:'filter',text:'筛选器',icon:'filter'}));
            htmls.push( '<div class="col-sm-12 no-padding top-border" style="margin-bottom: 10px;"></div>');
            htmls.push('</div>');
            htmls.push('<div class="crossTable"></div>');
            this.contentElem.html(htmls.join(''));
        },

        /**
         *
         * @private
         */
        _initAction:function () {
            this._on({
                'click [data-command="cubeDesignerCommand"]:not(.disabled)':function(event){
                    var elem = $(event.currentTarget);
                    var commandOptions = $.extend({},elem.data());
                    this.execCommand(event.currentTarget,commandOptions);
                },
                'click .measure-item>.dropdown>.dropdown-menu>.option-item':function (event) {
                    var itemElem = $(event.currentTarget);

                    var aggregateHandler = itemElem.parent().prev('.item-aggregate');

                    aggregateHandler.attr('data-value',itemElem.data('value')).text('('+itemElem.text()+')');
                },
                'click .query-item-remove':function (event) {
                    $(event.currentTarget).parents('.cube-query-item:first').remove();
                    this._drawMockCrossTable();
                },
                'blur .item-text':function (event) {
                    var elem = $(event.currentTarget),oldText = elem.data('text');
                    var text = elem.text();
                    if(!oldText || oldText!=text){
                        elem.text(text).data('text',text);
                        this._drawMockCrossTable();
                    }
                },
                'dropStop .dimension-container':function (event,ui) {
                    this._addDimension($(event.currentTarget),ui);
                }
            });
        },

        /************************************** method begin ************************************/
        addRowDimensionContainer:function(dom,commandOptions){
            var html = _buildCubeQueryRowHtml({id:'row',text:'行标签',icon:'list'},true);
            var lastRowContainer = this.element.find('.dimension-row:last').parent();
            $(html).insertAfter(lastRowContainer);
        },

        /**
         *
         * @param dom
         * @param commandOptions
         */
        removeDimension:function(dom,commandOptions){
            $(dom).parents('.dimension-item:first').remove();
            //生成示例结构
            this._drawMockCrossTable();
        },

        /**
         *
         */
        query:function(dom,commandOptions){
            this._openSubPage('crossTableViewer',{},{});
        },

        /************************************ method end ****************************************/
        /**
         *
         * @param container
         * @param item
         * @private
         */
        _addDimension:function (container,item) {
            //add 计量
            var itemHtmls = [];
            if(container.hasClass('dimension-measure')){
                itemHtmls.push(_buildDataItemHtml(item,'measure-item',_buildAggregateDropdown(item.expression)));
            }else{
                itemHtmls.push(_buildDataItemHtml(item,''));
            }
            container.append(itemHtmls.join(''));

            //生成示例结构
            this._drawMockCrossTable();
        },

        _findMeasureItems:function(){
            var measureItems = [];
            this.element.find('.dimension-container.dimension-measure>[data-column-name]').each(function () {
                var itemElem = $(this);
                measureItems.push({
                    id:itemElem.data('columnName'),
                    text:itemElem.find('.item-text:first').text()
                });
            });
            return measureItems;
        },

        /**
         * 模拟结构
         * @private
         */
        _drawMockCrossTable:function(){
            var mockCubes = this._mockCubes();
            if($.isArray(mockCubes) && mockCubes.length>0 ){
                this.crossTableElem.crossTable('drawCubes',this._mockCubes());
            }
        },

        /**
         *
         * @private
         */
        _mockCubes:function(){

            //宾栏
            var slaveDimensions =  _mockDimensions(this.element.find('.dimension-container.dimension-col'),'S',2),
                //mainDimensions =  _mockDimensions(this.element.find('.dimension-container.dimension-row'),'M',1),
                measureDimension = {id:'M',text:'记录',position:2,items:this._findMeasureItems()};

            var cubes = [],dimensions = [];
            //宾栏
            if(slaveDimensions && slaveDimensions.length){
                dimensions = dimensions.concat(slaveDimensions);
            }
            //记录
            if(measureDimension && measureDimension.items.length){
                dimensions.push(measureDimension);
            }

            this.element.find('.dimension-container.dimension-row').each(function (index) {
                cubes.push({
                    dimensions:[].concat(dimensions).concat(_mockDimensions($(this),'M'+index,1)||[])
                });
            });

            return cubes;
        },

        _resize:function () {
            this._resizePageHeight();
        }
    }));

    function _mockDimensions(dimensionContainer,prefix,position) {
        var dimensions = [];
        dimensionContainer.find('>[data-column-name]').each(function (index) {
            var id = prefix+index,
                text = $('.item-text:first',this).text();
            dimensions.push({
                id:id,
                text:text,
                position:position,
                items:_randomItems(id,text)
            });
        });
        return dimensions;
    }

    function _randomItems(id,text) {
        var items = [],
            count = Math.max((Math.random()*1000)%5,2);
        for(var i=0;i<count;i++){
            items.push({
                id:id+i,
                text:text+i
            })
        }
        return items;
    }

    function _buildDataItemHtml(dataItem,itemStyle,extraHtml) {
        var htmls = [];
        htmls.push('<span title="'+dataItem.text+'" data-column-name="'+dataItem.columnName+'" class="dimension-item '+itemStyle+'">');
        htmls.push( '<span class="youi-icon icon-columns"></span>');
        htmls.push( '<span data-text="'+dataItem.text+'" class="item-text" contenteditable="true">'+dataItem.text+'</span>');
        htmls.push(extraHtml||'');
        htmls.push('<span data-command="'+_COMMAND+'" data-name="removeDimension" class="youi-icon icon-remove"></span>');
        return htmls.join('');
    }

    /**
     *
     * @private
     */
    function _buildAggregateDropdown(aggregateExpression) {
        var htmls = [],aggregateExpression = aggregateExpression||'count';//如果是数值类型，默认为sum

        htmls.push('<span class="dropdown"><span class="dropdown-toggle item-aggregate" data-toggle="dropdown">('+aggregateExpression+')</span><ul class="dropdown-menu">');
        $(_AGGREGATES).each(function () {
            htmls.push('<li class="option-item" data-value="'+this+'">'+this+'</li>');
        });
        htmls.push('</ul></span>');
        return htmls.join('');
    }

    /**
     *
     * @param item
     * @returns {string}
     * @private
     */
    function _buildCubeQueryRowHtml(item,remove) {
        var htmls = [];
        htmls.push('<div class="no-padding cube-query-item">');
        htmls.push('<span class="dimension-label youi-icon icon-'+(item.icon||'')+'">');
        htmls.push((item.text||'')+(remove?'<span class="icon-remove query-item-remove"></span>':'')+'</span>');
        htmls.push('<div class="dimension-container dimension-'+item.id+'"></div></div>');
        return htmls.join('');
    }

})(jQuery);