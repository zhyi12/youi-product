/**
 * Created by zhouyi on 2019/10/19.
 */
(function($) {

    /**
     * 两个集合映射，通过三次贝塞尔曲线连接匹配的项
     */
    $.widget("youi.matcherList",$.youi.abstractWidget,$.extend({},{

        options:{
            bindResize:true,
            height:500,
            bezierDelt:0,
            matcherUrl:'',
            targetItems:'targetItems',
            sourceItems:'sourceItems'
        },

        /**
         *
         * @private
         */
        _initWidget:function () {
            this.element.height(this.options.height);
            this.contentElem = this.element.find('>.'+this.widgetName+'-content');
            this._initCanvasContext();//绘画面板支持

            if(this.options.matcherUrl){
                this._loadMatcher();
            }else{
                this._resize();
            }
        },

        _loadMatcher:function () {
            if(this.options.matcherUrl){
                $.youi.ajaxUtils.ajax({
                    url:this.options.matcherUrl,
                    success:this._proxy('_parseRecord')
                });
            }
        },

        /**
         *
         * @param result
         * @private
         */
        _parseRecord:function (result) {
            if(result && result.record){
                var sourceItems = result.record[this.options.sourceItems],
                    targetItems = result.record[this.options.targetItems];

                var sourceHtmls = ['<div class="source-container">'];
                var targetHtmls = ['<div class="target-container">'];

                $(sourceItems).each(function (index) {
                    sourceHtmls.push(_buildNodeHtml(this,index));
                });

                $(targetItems).each(function (index) {
                    targetHtmls.push(_buildNodeHtml(this,index));
                });

                sourceHtmls.push('</div>');
                targetHtmls.push('</div>');

                var htmls = [];
                htmls = htmls.concat(sourceHtmls).concat(targetHtmls);
                this.contentElem.html(htmls.join(''));
                //刷新线条
                this._refreshLines();
            }
        },

        /**
         * 初始化面板
         * @private
         */
        _initCanvasContext:function () {
            var main = document.createElement('canvas');
            this.element.append(main);

            this.canvasContext = main.getContext('2d');
        },

        /**
         * 刷新线条
         * @private
         */
        _refreshLines:function () {
            var height = this.element[0].scrollHeight,
                width = this.element.innerWidth();

            this.element.find('>canvas').width(width).height(height)
                .attr('width',width+'px').attr('height',height+'px');

            this.canvasContext.clearRect(0,0,width,height);
            //匹配的位置
            var matchedPoints = [],targetPosMap = {};

            this.element.find('.target-container>.node').each(function () {
                var nodeElem = $(this),
                    targetId = nodeElem.data('id');
                targetPosMap[targetId] = _calculateNodePos(nodeElem);
            });

            //匹配
            this.element.find('.source-container>.node').each(function () {
                var nodeElem = $(this),
                    matchedId = nodeElem.data('matchedId');

                if(targetPosMap[matchedId]){
                    matchedPoints.push({
                        source:_calculateNodePos(nodeElem),
                        target:targetPosMap[matchedId]
                    });
                }
            });

            for(var i=0;i<matchedPoints.length;i++){
                this._connectorMatchedPoint(matchedPoints[i]);
            }
        },

        /**
         *
         * @param matchedPoint
         * @private
         */
        _connectorMatchedPoint:function(matchedPoint){
            //计数控制节点
            var delt = this.options.bezierDelt;
            var controlPos1 = {x:matchedPoint.source.x+delt,y:matchedPoint.source.y+delt},
                controlPos2 = {x:matchedPoint.target.x-delt,y:matchedPoint.target.y-delt};
            _drawBezierLine(this.canvasContext,matchedPoint.source,controlPos1,controlPos2,matchedPoint.target);
        },

        /**
         *
         * @private
         */
        _resize:function () {
            this._refreshLines();
        }

    }));

    /**
     * 绘制三次贝塞尔曲线连接
     * @private
     */
    function _drawBezierLine(canvasContext,startPos,controlPos1,controlPos2,endPos) {
        canvasContext.strokeStyle = '#a6a9ff';
        canvasContext.beginPath();
        canvasContext.moveTo( startPos.x , startPos.y );
        canvasContext.bezierCurveTo( controlPos1.x , controlPos1.y  ,controlPos2.x , controlPos2.y  ,endPos.x , endPos.y);
        canvasContext.stroke();
    }

    /*
     * 绘制直线
     * 起始节点
     * 结束节点
     */
    function _drawStraightLine(context,startPoint,endPoint){
        context.moveTo(startPoint.x,startPoint.y);
        context.lineTo(endPoint.x,endPoint.y);
    }

    /**
     *
     * @private
     */
    function _calculateNodePos(nodeElem) {
        var offsetLeft = nodeElem[0].offsetLeft + nodeElem.parent()[0].offsetLeft;
        return {
            x:offsetLeft + nodeElem.width()/2,
            y:nodeElem[0].offsetTop + nodeElem.height()/2
        }
    }

    function _buildNodeHtml(node,index){
        var top = index*40+10;
        return '<div class="node" style="top:'+top+'px;" data-id="'+node.id+'" data-matched-id="'+(node.mappedId||'')+'">'+(node.text||node.id)+' '+(node.expression||'')+'</div>';
    }

})(jQuery);