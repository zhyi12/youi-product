/*!
 * youi JavaScript Library v3.0.0
 * 
 *
 * Copyright 2016-2020, zhyi_12
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Date: 2019-9-15
 */
(function($) {
	
	$.widget("youi.fieldQueryParam",$.youi.abstractWidget,$.extend({},$.youi.field,{
		/**
		 * 
		 */
		_initField:function(){
			
		},
		/**
		 * 
		 */
		_initAction:function(){
			//
		},

		loadParams:function (queryParams) {
			var paramMap = {},params = [];

			var htmls = [];
			$(queryParams).each(function () {
				if(!paramMap[this.property]){
                    htmls.push(_buildParamHtml(this));
				}
                paramMap[this.property] = true;
            });

			this.element.html(htmls.join(''));

        },
		
		
		_defaultHtmls:function(){
			
		},
		
		setValue:function(value){

		},
		
		getValue:function(){
			var values = [];
			this.element.find('input.form-control.text-input').each(function () {
				var inputElem = $(this),value = inputElem.val();
				if(value){
                    values.push({
                    	property:inputElem.attr('name'),
						value:value
					});
				}
            });
			return values;
		},
		
		clear:function(){
			
		}
	}));

    function _buildParamHtml(param){
    	var htmls = [];
        htmls.push('<div class="col-sm-4 input-group">');
        htmls.push('<label class="input-group-addon">'+(param.text||param.property)+'</label>');
        htmls.push('<input class="form-control text-input" name="'+param.property+'" type="text"></input>');
        htmls.push('</div>');

    	return htmls.join('');

	}
})(jQuery);