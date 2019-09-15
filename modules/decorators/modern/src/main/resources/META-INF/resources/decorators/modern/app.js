/*!
 * youi JavaScript Library v3.0.0
 * 
 *
 * Copyright 2016-2020, zhyi_12
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Date: 2016-1-4
 */
(function($) {
	/**
	 * @version 1.0.0
	 */
	
	var HASH_PAGE_PREFIX = $.youi.pageHashPrefix;
	//beforeunload需放在组件外绑定
    $(window).bind('beforeunload',function(event){
        if($.youi.pageUtils.checkDirty()){
            return;
        }
        return '有数据尚未保存';
    });
	
	$.widget("youi.app",$.youi.abstractWidget,$.extend({},{
		options:{
			bindResize:true
		},
		/**
		 * 
		 */
		_initWidget:function(){
			
		},
		/**
		 * app 事件
		 */
		_initAction:function(){
			//hash变化
			$(window).bind('hashchange',function(event){
				_goPageHash();//根据location.hash 定位页面
			}).bind('dirty',function(event){
				$('[data-dirty]').trigger('dirtyChange');
            });
			/*
			 * 全局动作监听
			 */
			this._on({
				'click a:not(.page-link)':function(event){
					//屏蔽元素的默认动作
					event.preventDefault();
				},
				'click a[data-menu-clear]':function(event){
					//屏蔽元素的默认动作
					$('.menu-item.selected').removeClass('selected');
				},
				'click .page-nav>.title-text':function (event) {
					var elem = $(event.currentTarget);
					if((elem.next().length||elem.prev().length)&&!elem.hasClass('active')){
                        if(elem.hasClass('title-second')){
                            this.element.find('.page-container.second-page:first').removeClass('hide');
                            this.element.find('.page-container:first').addClass('hide');
                            elem.prev().removeClass('active');
						}else{
                            this.element.find('.page-container.second-page:first').addClass('hide');
                        	this.element.find('.page-container.hide:first').removeClass('hide');
                            elem.next().removeClass('active');
						}
                        elem.addClass('active');
					}
                },
				'openSecondPage':function (event,ui) {
					var pageTitleElem = this.element.find('.page-nav>.title-text:first').removeClass('active'),
						secondTitleElem = pageTitleElem.next();

					if(secondTitleElem.length==0){
                        secondTitleElem = $('<span class="title-text title-second active"></span>').insertAfter(pageTitleElem);
					}
                    secondTitleElem.html(ui.title+'<span class="second-page-close icon-remove"></span>').addClass('active');

                    this.element.find('.page-container.second-page:first').removeClass('hide');
					//resize secondPage
					this._resizeSecondPage();
                },

				'pageResize .youi-page':function(event,ui){
                    var pageElem = $(event.currentTarget);
                    var height = pageElem.parent().height();
                    ui = ui||{};
					var headerHeight = ui.headerHeight||2;
					pageElem.find('>.page-inner-height:visible').height(height - headerHeight).each(function () {
						var height = $(this).innerHeight();
                        var fixedHeight = 0;
						$('>.fixed-height',this).each(function () {
                            fixedHeight+=$(this).outerHeight();
                        });
						$('>.auto-height:visible:first').height(height - fixedHeight - 5);
                    });

					return false;
				},

				'click .page-nav>.title-second>.second-page-close':function (event,ui) {
					var elem = $(event.currentTarget).parent();
                    elem.prev('.title-text').addClass('active');
                    elem.remove();
                    this.element.find('.second-page:first').remove();
                    this.element.find('.page-container:first').removeClass('hide');
                }
			});
			//由hash变化触发
			this.element.find('.page-container:first').bind('pagehash',function(event,ui){
                if(!$.youi.pageUtils.checkDirty()){
                    return;
                }

				if(event.target===this){
					//页面加载
					var pageContainer = $(event.currentTarget);
					var currentHash = pageContainer.attr('data-hash');

					//var queryParams =
					if(ui.hash!=currentHash){
                        //加载页面
                        pageContainer.attr('data-hash',ui.hash);
                        //pageContainer.
                        //如果有参数，直接执行goPageHash(1)
						if(ui.hash.split('?')[0]==currentHash.split('?')[0]){
							return;//不重新加载page
						}
                        //ui.hash
						$.youi.pageUtils.loadPage(pageContainer,ui.hash,function(){
							//页面加载完成后，执行goPageHash方法，处理页面中的hash跳转
                            $('>.youi-page',this).height($(this).parent().innerHeight() - 16).trigger('pageResize',{headerHeight:38});
                            pageContainer.next('.page-container.second-page').remove();
                            _goPageHash(1);
						});
					}
				}
			}).bind('setpagehash',function(event,ui){
                //page-container容器设置location的hash值
                if(!$.youi.pageUtils.checkDirty()){
                    return;
                }
				if(ui.hash){
					window.location.hash = 'p:'+ui.hash;
				}
				return false;
			});
			
			//默认的pageHash
			_goPageHash();
		},

        _resizeSecondPage:function (skipResizeWidget) {
			var height = this.element.find('.layout-panel.panel-center:first').height();
			var secondPage = this.element.find('.page-container.second-page:visible:first');
            secondPage.height(height - 10);

            var pageElem = secondPage.find('>.youi-page:first').trigger('pageResize',{headerHeight:38});

            if(!skipResizeWidget){
                $.youi.widgetUtils.triggerResize(pageElem,true);
			}
        },

		_resize:function () {
            this._resizeSecondPage(true);
        }

		
	}));
	
	/**
	 * 加载页面
	 */
	function _goPageHash(startIndex){
		var startIndex = startIndex||0;
			hash = window.location.hash,
			partHash = '',pagehash='';
		if(hash.indexOf(HASH_PAGE_PREFIX)===0){
			var splitIndex = hash.indexOf(':');
			if(splitIndex>HASH_PAGE_PREFIX.length+1){
				partHash = hash.substring(HASH_PAGE_PREFIX.length+1,splitIndex);
			}
			pagehash = hash.substring(splitIndex+1);
		}else{
			return;
		}
		
		if(!startIndex){
			//菜单选中处理

			$('.page-container:first').removeClass('hide');

			var menuHref = pagehash.split('?')[0];
			var currentSelectMenu  = $('.youi-menu:first .menu-item a.page-link[href="'+menuHref+'"],.menu-item a.page-link[data-href="'+menuHref+'"]').parents('li.menu-item:first');

			$('.youi-menu:first .menu-item.selected').not(currentSelectMenu).removeClass('selected');
			currentSelectMenu.addClass('selected');

			currentSelectMenu.parents('.menu-bar-content:visible:first').addClass('active').prev().addClass('active');

			currentSelectMenu.parents('.menu-item.expandable').addClass('expanded');
		}else if($('.menu-bar-content.active').length==0){
			$('.menu-bar-content:first').addClass('active').prev().addClass('active');
		}
		
		//触发pagehash动作
		$('body',document).find('[data-hash]:visible').each(function(index){
			if(index>=startIndex){
				$(this).trigger('pagehash',{hash:pagehash,partHash:partHash||$(this).data('partHash')});
			}
		});
	}
	
})(jQuery);