/**
 * Created by zhouyi on 2019/9/6.
 */

(function($) {

    $.widget("youi.formFields",$.youi.abstractWidget,$.extend({},{

        options:{
            columns:2,
            getFieldItemsUrl:''
        },

        /**
         *
         * @private
         */
        _initWidget:function () {
            this.element.addClass('form-horizontal');
            this.contentElem = this.element.find('>.'+this.widgetName+'-content:first');
            this.loadFields();
        },

        loadFields:function () {
            if(this.options.getFieldItemsUrl){

                $.youi.ajaxUtils.ajax({
                    url:this.options.getFieldItemsUrl,
                    success:this._proxy('_parseRecords')
                });
            }
        },

        /**
         *
         * @param result
         * @private
         */
        _parseRecords:function (result) {
            this._loadFormFields(result.records);
        },

        /**
         *
         * @private
         */
        _loadFormFields:function (fields) {
            var fieldModels = [];
            $(fields).each(function () {
                var fieldModel = $.extend({},this);
                fieldModel.fieldType = fieldModel.fieldType||'fieldText';
                fieldModels.push(fieldModel);
            });

            this.contentElem.html(_buildLayoutHtml(fieldModels,this.options.columns));

            this.contentElem.find('.youi-field[data-property]').each(function (index) {
                var fieldModel = fieldModels[index];
                $(this)[fieldModel.fieldType](fieldModel);
            });

            this.contentElem.fieldLayout({
                fields:fields
            });
        }

    }));

    function _buildLayoutHtml(fieldModels,columns,widths) {
        var fieldHtmls = [],columns=columns||2,fieldIndex=0,widths = widths||[],
            cellCol = 12/columns;

        $(fieldModels).each(function (index) {
            if(this.type=='fieldHidden'){
                fieldHtmls.append(_createFieldHtml(this));
                return;
            }

            var column = _parseFieldColumn(this.column||1,columns),
                colIndex = fieldIndex%columns,labelWidth = 80;

            fieldIndex = fieldIndex+column;

            if(colIndex<widths.length){
                labelWidth = widths.get(colIndex);
            }

            var groupStyles = [];

            groupStyles.push("form-group col-sm-"+Math.min(12,cellCol*column)+" label-"+labelWidth+"");

            if(this.notNull){
                groupStyles.push("notNull");
            }

            fieldHtmls.push("<div class=\""+groupStyles.join(' ')+"\">");
            fieldHtmls.push(	"<label class=\"control-label field-label\">");
            fieldHtmls.push(	this.caption==null?"":(this.caption+"："));
            fieldHtmls.push(	"</label>");
            fieldHtmls.push(	_createFieldHtml(this));
            fieldHtmls.push("</div>");
        });

        return fieldHtmls.join('');
    }

    function _parseFieldColumn (column,columns) {
        if(column<0){
            column = 1;
        }
        //columns总列数  column 占位数
        if(columns>columns||columns==5){
            column = 6;
        }
        return column;
    }

    /**
     *
     * @param fieldModel
     * @private
     */
    function _createFieldHtml(fieldModel) {
        var htmls = ['<div data-property="'+fieldModel.property+'" class="youi-field '+fieldModel.type+'">'];
        htmls.push('</div>');
        return htmls.join('');
    }
})(jQuery);