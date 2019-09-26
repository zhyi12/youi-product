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
                htmls.push('<div><div>'+iTag+'</div>');
                htmls = htmls.concat(tagHtmls[iTag]);
                htmls.push('</div>');
            }

            this.element.html(htmls.join(''));
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

        htmls.push('<div class="option-item bg-info ">');

        htmls.push('<span class="col-sm-1">'+httpMethod+'</span>');
        htmls.push('<span class="col-sm-11">'+path+'</span>');

        htmls.push('</div>');

        return htmls.join('');
    }

})(jQuery);