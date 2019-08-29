/**
 * Created by zhouyi on 2018/8/7.
 */

/**
 *
 */
//require("./../youi.core.js");
(function($) {

    var _STACK_URL_SPLIE_ = '###nodeId:';
    /**
     * 页面导航
     */
    $.widget("youi.treePage",$.youi.abstractWidget,$.extend({},{

        options:{
            bindResize:true
        },

        /**
         *
         * @private
         */
        _initWidget:function () {
            this.contentElem = this.element.find('>.treePage-content:first').addClass('auto-height');
            this.treeElem = $('#'+this.options.refs[0]);
            this.element.attr('data-hash',true);
            
            window[this.options.refs[0]+'_select'] = this._proxy('_treeNodeSelect');
            window[this.options.refs[0]+'_afterLoad'] = this._proxy('_treeAfterLoad');

            this.element.trigger('pagehash',{init:true,startPage:this._parsePageUrl()||this.options.startPage});
            this._bindWidgetCommand();

            if(this.options.contentWidget){
                this.contentElem[this.options.contentWidget](this.options);
            }
        },

        _parsePageUrl:function () {
            var hash = window.location.hash;//
            var startIndex = hash.indexOf(_STACK_URL_SPLIE_);

            if(startIndex>-1){
                return hash.substring(startIndex+_STACK_URL_SPLIE_.length);
            }
            return null;
        },

        _treeAfterLoad:function (result) {
            this._callGloablFunc('afterLoad');
            var openPath = this.treeElem.data('openPath');

            if(openPath){
                window.setTimeout(this._proxy('_openPath',openPath),200);
            }

            this.treeElem.find('li.root').removeClass('expanded').removeClass('expandable');
        },

        loadTreeNodes:function (treeNodes) {
            var htmls = [];
            if(treeNodes){
                $(treeNodes).each(function(){
                    htmls.push($.youi.treeUtils.treeNodeHtml(this.id,this.text,{group:this.group},this));
                });
                this.treeElem.find('>ul').html(htmls.join(''));

                if(this.options.sourceStyle&&window.location.hash.indexOf('treepage###')==-1){
                    var openPath = this.treeElem.find('.treeNode.'+this.options.sourceStyle+':first').attr('id');
                    window.setTimeout(this._proxy('_openPath',openPath),100);
                }
            }
        },

        _openPath:function (openPath) {
            this.treeElem.tree('openPath',openPath);
        },

        /**
         *
         * @param treeNode
         * @private
         */
        _treeNodeSelect:function (treeNode) {
            var treeNodeId = treeNode.attr('id');

            var treePaths = [];

            try{
                this.treeElem.tree('visitParents',treeNode,function (treeNodeDom) {
                    treePaths.push(treeNodeDom.getAttribute('id'));
                });
                treePaths = treePaths.reverse();
                treePaths.push(treeNodeId);
                this.element.trigger('setpagehash',{hash:treePaths.join('/'),partHash:'treePage'});
            }catch(err){
                console.log(err);
            }

            this._callGloablFunc('select',treeNode);
        },

        _initAction:function () {
            this._on({
                'pagehash': function (event, ui) {//执行pageHash方法
                    var hash = window.location.hash;//
                    if(ui.init){
                        if(hash.indexOf('?')==-1){
                            window.location.hash = window.location.hash+"?treepage";
                        }
                    }

                    var splitIndex = hash.indexOf(_STACK_URL_SPLIE_);
                    
                    if(splitIndex>-1){
                        //执行树节点动作
                        var treeOpenPath = hash.substring(splitIndex+_STACK_URL_SPLIE_.length);
                        var treePaths = treeOpenPath.split('/');
                        var treeNodeId = treePaths[treePaths.length-1];

                        var treeNodeElem = this.treeElem.find('li.treeNode#'+treeNodeId);

                        if(ui.init){
                            this.treeElem.attr('data-open-path',treeOpenPath);
                            //
                            this.element.trigger('resize');
                        }else if(treeOpenPath){
                            //回退类型的树节点定位
                            this.treeElem.tree('openPath',treeOpenPath);

                        }
                        //this._callGloablFunc('select',treeNodeElem);
                    }
                    return false;
                },'setpagehash':function(event,ui){
                    var hash = window.location.hash;//
                    if(ui.hash&&ui.partHash){
                        var splitIndex = hash.indexOf(_STACK_URL_SPLIE_);
                        if(splitIndex!=-1){
                            window.location.hash=hash.substring(0,splitIndex)+_STACK_URL_SPLIE_+ui.hash;
                        }else{
                            window.location.hash=hash+_STACK_URL_SPLIE_+ui.hash;
                        }
                    }
                    return false;
                }
            });
        },

        _afterLoadPage:function () {

        },

        loadHtml:function (html) {
            this.contentElem.html(html);
        },

        loadPage:function (pageUrl) {
            $.youi.pageUtils.loadPage(this.contentElem,pageUrl,this._proxy('_afterLoadPage'));
        },

        _resize:function () {
            this._resizePageHeight();
        }

    }));

})(jQuery);