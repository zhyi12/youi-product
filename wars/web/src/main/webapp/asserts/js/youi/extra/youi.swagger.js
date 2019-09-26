/**
 * Created by zhouyi on 2019/9/2.
 */
(function($) {

    $.widget("youi.swagger",$.youi.abstractWidget,$.extend({},{


        options:{
            apiUrl:''
        },


        /**
         *
         * @private
         */
        _initWidget:function () {
            this.contentElem = this.element.find('>.'+this.widgetName+'-content').accordion();
            this._loadApi();
        },


        _loadApi: function () {
            if(this.options.apiUrl){
                $.youi.ajaxUtils.ajax({
                    url:this.options.apiUrl,
                    success:this._proxy('_parseSwaggerDocs')
                });
            }
        },

        /**
         *
         * @param result
         * @private
         */
        _parseSwaggerDocs:function (result) {
            var tagHtmls = {},htmls = [];

            $.map(result.paths,function(pathItems,path){
                if(path.indexOf('/services/')==0){
                    $.map(pathItems,function (item,httpMethod) {
                        var tag = item.tags[0];

                        if(!tagHtmls[tag]){
                            tagHtmls[tag] = [];
                        }
                        tagHtmls[tag].push(_buildApiItemHtml(httpMethod,item,path));
                    });
                }
            });

            for(var iTag in tagHtmls){
                htmls.push('<div class="youi-panel panel panel-default"><div style="padding:5px;" class="panel-heading panel-title">'+iTag+'</div><div class="panel-body no-padding">');
                htmls = htmls.concat(tagHtmls[iTag]);
                htmls.push('</div></div>');
            }

            this.contentElem.html(htmls.join(''));
        }

    }));

    /**
     *
     * @param httpMethod
     * @param apiItem
     * @param path
     * @returns {string}
     * @private
     */
    function _buildApiItemHtml(httpMethod,apiItem,path) {
        var htmls = [];

        htmls.push('<div class="option-item">');

        htmls.push('<span class="col-sm-1">'+httpMethod+'</span>');
        htmls.push('<span class="col-sm-6">'+path+'</span>');
        htmls.push('<span class="col-sm-5">'+(apiItem.description||'')+'</span>');

        htmls.push('</div>');

        return htmls.join('');
    }

})(jQuery);