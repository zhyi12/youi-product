/*!
 * youi JavaScript Library v3.0.0
 * 
 *
 * Copyright 2015, zhyi_12
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Date: 2015-1-27
 */
(function($) {
	'use strict';
	
	$(window).bind('resize',function(event){
		window.setTimeout(function(){
			$.youi.widgetUtils.triggerResize(null,true);
		});
		
		$('.ui-widget-overlay:visible').each(function(){
			$(this).css({
				width:$(window).width(),
				height:$(window).height()
			});
		});
	});
	
	$('body',document).bind('pagetitle',function(event,ui){
		if(ui.title){
			$('.page-nav:first').html('<span class="title-text active">'+ui.title||''+'</span>');
		}
	});
	
	var _SYSTEM_MENU_ID = 'system_h_menu';
	
	$.widget("youi.bootstrapLayout",$.youi.abstractWidget,$.extend({},{
		/**
		 * 默认参数
		 */
		options:{
			bindResize:true
		},
		/**
		 *
		 * 接口方法，初始化变量模型
		 */
		_initModel:function(){
			
		},
		/**
		 * 接口方法，初始化widget组件
		 */
		_initWidget:function(){
			this.middleElement = this.element.find('>.layout-md');
			this._resize();
		},
		
		/**
		 * 接口方法，填充html元素
		 */
		_defaultHtmls:function(){
			
		},
		/**
		 * 接口方法，初始化组件动作
		 */
		_initAction:function(){
			//resize动作集成
			
			this._on({
				'mouseover #system_h_menu .menu-item>.dropdown-toggle':function (event,ui) {
					var elem = $(event.currentTarget);
					var menuElem = elem.parent().addClass('open');
                    menuElem.prevAll().removeClass('open');
                    menuElem.nextAll().removeClass('open');
                    if(menuElem.parent().is('.menu-container-root')){
                    	//对下级节点样式进行调整
                    	var childrens = menuElem.children(".dropdown-menu").children(".menu-item");

                        childrens.each(function(){
                            var adjustElem = this,
                                enkelHeight = $(adjustElem).children(".dropdown-menu").height();
                            $(adjustElem).css({
                                'margin-bottom':enkelHeight+10
                            });
						});
					}
                },
				'click #system_h_menu a.page-link':function (event) {
					var elem = $(event.currentTarget);

                    //菜单点击
                    var href = elem.attr('href');

                    if(href==='#'){
                        return;
                    }

                    if(href&&href.indexOf('#')!=0&&href!='javascript:void(0)'){
                        $(event.currentTarget).attr('data-href',href);
                        href = '#p:'+href;
                    }

                    window.location.hash = href;

                    var menuItem = $(event.currentTarget.parentNode.parentNode);
                    if(menuItem.is('.selected')){
                        return false;
                    }else{
                        this.element.find('.selected.menu-item').removeClass('selected');
                        menuItem.addClass('selected');
                        $(event.currentTarget).attr('href',href);
                    }
                }
			});
		},

		/**
		 * 接口方法，重定位
		 */
		_resize:function(){
			var height = $(window).height();

			var oHeight = 5;
			this.element.find('>div.layout-panel,>div.layout-md').not(this.middleElement).each(function(index){
				oHeight += $(this).outerHeight();
			});
//
			var height = height - oHeight;
			this.middleElement.css('minHeight',height);
			this.middleElement.find('>div:visible').css('height',height);

			var pageContainer = this.middleElement.find('.page-container:visible:first');
			var visiblePageElem = pageContainer.find('>.youi-page:first').height(height-36);

            visiblePageElem.find('>.page-inner-height').height(height - 45);
            visiblePageElem.find(':visible[data-resize="true"]').trigger('widget.resize',{forceResize:true});
        },
		/**
		 * 接口方法，销毁组件
		 */
		_destroy:function(){
			this.middleElement = null;
		}
	}));
	
})(jQuery);