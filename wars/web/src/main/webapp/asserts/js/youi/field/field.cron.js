/**
 * field组件
 * Copyright (c) 2009 zhouyi
 * licenses
 * doc 
 */
(function($) {
	var _log = $.youi.log;
	/**
	 * fieldCron
	 * 
	 */
	$.widget("youi.fieldCron",$.youi.abstractWidget,$.extend({},$.youi.field,{
		
		_defaultHtmls:function(){
			var htmls = [];
			htmls.push('<table width="'+this.options.width+'px" class="cronTable" cellpadding="1" cellspacing="1">');
			var names = ['秒','分','时','日期','月','星期','年'];
			var modes = [
					     {value:'char1',text:' * '},
					     {value:'char2',text:' ? '},
					     {value:'sect',text:'区间'},
					     {value:'addo',text:'增量'},
					     {value:'free',text:'自由'},
					     {value:'empty',text:'空'}
					];
			
			
			var cronLabelHtmls = [];
			for(var i=0;i<7;i++){
				htmls.push('<tr>');
				htmls.push('<td class="label">'+names[i]+':</td>');
				
				for(var j=0;j<modes.length;j++){
					htmls.push('<td class="modePanel '+(j==0?'show ':'')+modes[j].value+'">'+this['_'+modes[j].value+'Html']()+'</td>');
				}
				
				htmls.push('<td class="modeSwitch">');
				htmls.push(_buildModeSwitchHtml(modes,i));
				htmls.push('</td>');
				
				htmls.push('</tr>');
				
				cronLabelHtmls.push('<td class="cronLabel cronLabel-'+i+'"> * </td>');
			}
			
			htmls.push('</table>');
			
			htmls.push('<div class="cron-expression"><table><tr><td>周期表达式：</td>');
			htmls = htmls.concat(cronLabelHtmls);
			htmls.push('</tr></table><div>');
			htmls.push('<input type="hidden" class="value"/><div class=\"field-invalid\"></div>');
			
			this.element.append(htmls.join(''));
		},
		/***********************************/
		_char1Html:function(){
			return ' *(每一时刻) <input  class="mode-value" type="hidden" value="*"/>';
		},
		
		_char2Html:function(){
			return ' ?(占位符)<input  class="mode-value" type="hidden" value="?"/>';
		},

		_sectHtml:function(){
			var htmls = [];
			htmls.push('<input class="mode-value" value="1" type="text" size="6"/>(起始)');
			htmls.push(' - <input class="mode-value" type="hidden" value="-"/>');
			htmls.push('<input class="mode-value" value="1" type="text" size="6"/>(结束)');
			return htmls.join('');
		},
		
		_emptyHtml:function(){
			return '';
		},
		
		_addoHtml:function(){
			var htmls = [];
			htmls.push('<input class="mode-value" value="1" type="text" size="6"/>(起始)');
			htmls.push(' / <input class="mode-value" type="hidden" value="/"/>');
			htmls.push('<input class="mode-value" value="1" type="text" size="6"/>(增量)');
			return htmls.join('');
		},
		
		_freeHtml:function(){
			return '<input class="mode-value" type="text" size="15"/>';
		},
		/***********************************/
		/**
		 * 初始化动作
		 */
		_initAction:function(){
			this.element.delegate('input.mode','click',function(event){
				var value = $(this).val();
				var rowDoc = $(this).parents('tr:first');
				$('.modePanel',rowDoc).not('.'+value).removeClass('show');
				
				var showPanel = $('.modePanel.'+value,rowDoc);
				showPanel.addClass('show');
				
				var rowIndex = rowDoc.prevAll().length;
				//
				$('.cron-expression .cronLabel.cronLabel-'+rowIndex,event.livedFired).text(_getModeValue(showPanel));
				
				rowDoc = null;
			}).delegate('input.mode-value','change',function(event){
				//刷新值
				var rowDoc = $(this).parents('tr:first');
				var showPanel =  $(this).parents('td.modePanel:first');
				var rowIndex = rowDoc.prevAll().length;
				$('.cron-expression .cronLabel.cronLabel-'+rowIndex,event.livedFired).text(_getModeValue(showPanel));
			});
		},
		
		setValue:function(value){
			//this.element.find('input.value').val(value);
			//设置为自由模式
			this.element.find('input.mode[value=free]').click();
			var valueArray = value.split(' ');//使用空格切分
			
			this.element.find('.modePanel.free .mode-value').each(function(index){
				var value = valueArray[index]||'';
				$(this).val(value);
				$('.cron-expression .cronLabel.cronLabel-'+index,$(this).parents('.youi-fieldCron:first')).text(value);
			});
		},
		
		getValue:function(){
			var valueArray = [];
			this.element.find('.cron-expression .cronLabel').each(function(){
				var value = $(this).text();
				if(value){
					valueArray.push(value);
				}
			});
			return valueArray.join(' ');
		},
		
		clear:function(){
			this.element.find('input.mode[value=char1]').click();
			this.element.find('.cron-expression .cronLabel').text('*');
		},
		
		validate:function(){
			
		}
		
	}));
	/**
	 * 
	 */
	function _buildModeSwitchHtml(modes,postfix){
		var htmls = [];
		
		for(var i=0;i<modes.length;i++){
			if(modes[i].value=='char2'&&!(postfix==3||postfix==5)){
				continue;
			}
			
			if(modes[i].value=='empty'&&postfix!=6){
				continue;
			}
			
			htmls.push('<input class="mode" type="radio"');
			if(i==0){
				htmls.push(' checked="checked" ');
			}
			htmls.push(' name="mode');
			htmls.push(postfix)
			htmls.push('" value="');
			htmls.push(modes[i].value);
			htmls.push('">');
			htmls.push(modes[i].text);
			htmls.push('</input>');
		}
		
		return htmls.join('');
	}
	
	/**
	 * 
	 */
	function _getModeValue(panel){
		var values = [];
		panel.find('input.mode-value').each(function(){
			values.push($(this).val());
		});
		return values.join('');
	}
})(jQuery);