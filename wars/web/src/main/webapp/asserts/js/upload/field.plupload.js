/*!
 * youi JavaScript Library v3.0.0
 * 
 *
 * Copyright 2015, zhyi_12
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Date: 2015-7-2
 */

require("./field.core.js");
(function($) {
	var _log = $.youi.log;

	$.youi.uploadUtils = $.extend($.youi.uploadUtils,{
	    getImageSrc:function(refPath){
	        return $.youi.serverConfig.contextPath+'upload/download/'+refPath+'.html';
	    },

	    getThumbnailSrc:function(refPath,width,height){
	        width  = width||30;
	        height = height||30;
            return $.youi.serverConfig.contextPath+'img/thumbnail/'+width+'/'+height+'/'+refPath+'.html';
        }
	});
	/**
	 * fieldPlupload组件
	 */
	$.widget("youi.fieldPlupload",$.youi.abstractWidget,$.extend({},$.youi.field,{
		
		/**
		 * 参数配置
		 */
		options:{
			uploadUrl:'zuul/fileserver/upload.json',
			flash_swf_url:'asserts/js/upload/Moxie.swf',
			silverlight_xap_url:'asserts/js/upload/Moxie.xap',
			serverFileCaption:'在服务器中选择',
            // serverFileSrc:'',searchServerFileSrc:''
			maxSize:'1000mb',
			limits:10,//最大允许上传的文件个数
			mineTypeCaption:'',
			mineTypeExtensions:''
		},
		
		_initField:function(){
			var that = this;
			
			var filters = {max_file_size : this.options.maxSize},
                headers = {'X-CSRF-TOKEN':$.youi.serverConfig.csrfToken};
			
			if(this.options.mineTypeExtensions){
				filters.mime_types = [{title:this.options.mineTypeCaption||'',extensions:this.options.mineTypeExtensions}];
			}
			
			var uploader = new plupload.Uploader({
				runtimes : 'html5,flash,silverlight,html4',
				browse_button : this.options.id+'_pickfiles', // you can pass an id...
				container: this.element[0], // ... or DOM Element itself
				url : $.youi.serverConfig.contextPath+this.options.uploadUrl,
				flash_swf_url : $.youi.serverConfig.contextPath+this.options.flash_swf_url,
				silverlight_xap_url : $.youi.serverConfig.contextPath+this.options.silverlight_xap_url,
				filters :filters,
				headers:headers,
				init: {
					PostInit: function() {
						//
					},

					FilesAdded: function(up, files) {
					    if(that.checkLimits(files.length)){
					        $(files).each(function(){
                            	that._addFile(this);
                            });
					    }else{
					        $(files).each(function(){
                                up.removeFile(this.id);
                            });
                            $.youi.messageUtils.showError('\n最大允许上传'+that.options.limits+'个文件');
					    }
					},

					UploadProgress: function(up, file) {
//						document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
						that._uploadProgress(file);
					},
					
					FileUploaded:function(up,file,result){
						that._fileUploaded(file,result);
					},
					
					UploadComplete:function(){
						that._uploadComplete();
					},

					Error: function(up, err) {
					    var errorMsg = '';
					    console&&console.log(err.message);
					    if(err.code===-600){
					        errorMsg = '最大允许上传'+filters.max_file_size+'文件。';
					    }else if(err.code===-601) {
                            errorMsg = '请上传标准格式的文件。';
                        }else{
					        errorMsg = err.code + ": " + err.message;
					    }
						$.youi.messageUtils.showError("\n" + errorMsg);
					}
				}
			});
			//初始化上传组件
			uploader.init();
			
			this.uploader = uploader;

			if(this.options.serverFileSrc){
				var that = this;
				this.element.find('.srever-files .youi-tree').tree({
					iteratorSrc:$.youi.serverConfig.contextPath+this.options.serverFileSrc,
                    iteratorParentAttr:'parentId',
					idAttr:'internalFileId',
					textAttr:'internalFileCaption',
					root:'全部文件'
                    // select:function (event,ui) {
						// if(ui&&ui.selectedNode){
                    //         var file = $(ui.selectedNode).data();
                    //         that._selectFromServerFile(file);
						// }
                    // }
				});
			}
		},

        _selectFromServerFile:function (file) {
			if(!file.storePath)return;

			file.percent = 100;
			if(this.checkLimits(1)){
                this._addFile(file);
                this._fileUploaded(file,{response:file.storePath});
                this._uploadProgress(file);
                this._disabledUploadButton();
			}else{
                $.youi.messageUtils.showError('\n最大允许上传'+this.options.limits+'个文件');
			}
        },
		
		_defaultHtmls:function(){
			
			var htmls = [];
			
			htmls.push('<div class="btn-group btn-group-sm">');
			htmls.push('<span class="btn btn-default" id="'+this.options.id+'_pickfiles">选择文件</span><span class="btn btn-primary disabled" data-command="fieldCommand" data-name="startUpload" >上传文件</span>');
			this.options.serverFileSrc&&htmls.push(this._buildServerFilePanelHtml());
			htmls.push('</div>');
			htmls.push('<div class="file-upload-container"></div>');
			
			this.element.append(htmls.join(''));
		},

		_buildServerFilePanelHtml:function () {
            var htmls = [];
            htmls.push('<span class="btn btn-default dropdown" id="'+this.options.id+'_pickServerfiles">');

            htmls.push('<span class="dropdown-toggle" role="button" data-toggle="dropdown">'+this.options.serverFileCaption+'<b class="caret"></b></span>');
			htmls.push('<div class="dropdown-menu srever-files"><div class="youi-tree"><ul>')
            //htmls.push($.youi.treeUtils.treeNodeHtml('aaaa','文件',{src:$.youi.serverConfig.contextPath+this.options.serverFileSrc}));
			htmls.push('</ul></div></div></div>');
            htmls.push('</span>');

            return htmls.join('');
        },
		
		_initAction:function(){
			this._on({
				'click [data-name=startUpload]':function(){
					this._startUpload();
					return false;
				},'click [data-name=removeFile]':function(event){
					var item = $(event.currentTarget.parentNode);
					this.uploader.removeFile(item.data('fileId'));
					item.remove();
					
					if(this.element.find('.preupload').length==0){
						this._disabledUploadButton();
					}
					return false;
				},
				'click .dropdown-menu.srever-file':function (event) {

                    return false;
                },
				'dblclick .tree-span.file':function (event) {
					this._selectFromServerFile($(event.currentTarget).parent().data());
                }
			});
		},

		checkLimits:function(fileCount){
		    var exsitCount =  this.element.find('.file-upload-container>.option-item').length;
		    var allCount = exsitCount+fileCount;
		    return allCount<=this.options.limits;
		},
		
		_addFile:function(file){
			var showFileSize = _calShowFileSize(file.size)||'';
			
			this.element.find('.file-upload-container').append('<div class="option-item preupload" data-file-id="'+file.id+'"><span data-name="removeFile" class="youi-icon icon-remove" title="移除文件"></span><span class="upload-info">'+file.name+' '+showFileSize+'</span><span class="percent"></span></div>');
			this.element.find('.disabled').removeClass('disabled');
		},
		
		_startUpload:function(){
			this.uploader.start();
		},
		
		_uploadProgress:function(file){
			this.element.find('.file-upload-container [data-file-id='+file.id+'] .percent').html('('+file.percent + '%)');
		},
		
		_fileUploaded:function(file,result){
			var filePath = result.response;
			if (isJsonFormat(result.response)) {
                var record = JSON.parse(result.response);
                if (record.message && record.message.code === '999999') {
                    var errorMsg = record.message.info||'请上传标准格式的文件。';
                    $.youi.messageUtils.showError("\n" + errorMsg);
                    return false;
                }
                if (record.message && record.message.code === '000000') {
                    filePath = record.message.info||'';
                }
			}
			var fileElem = this.element.find('.file-upload-container [data-file-id='+file.id+']');
			fileElem.removeClass('preupload').attr('data-value',filePath);
			if(this.options.showImage){
			   var infoElem = fileElem.find('.upload-info')
			   $('<img src="'+$.youi.uploadUtils.getThumbnailSrc(filePath,50,50)+'"/>').insertAfter(infoElem);
			   infoElem.remove();
			}
			//回调函数
			this._callGloablFunc('afterFileUpload', filePath);
		},
		
		_uploadComplete:function(){
			//
			this._disabledUploadButton();
		},
		
		_doFieldValidate:function(value,record){
			//
			if(this.element.find('.preupload').length>0){
				this._validateError('还有未上传的文件');
			}
		},
		
		_disabledUploadButton:function(){
			this.element.find('[data-name=startUpload]').addClass('disabled');
		},
		
		getValue:function(){
			var values = [];
			this.element.find('.file-upload-container [data-value]').each(function(){
				values.push($(this).data('value'));
			});
			return values;
		},
		
		setValue:function(value){
		    if(this.options.showImage===true){
		        var values = [];
                if($.isArray(value)){
                    values = value;
                }else if(value){
                    values.push(value);
                }

                var htmls = [];
                $(values).each(function(){
                    htmls.push(_buildImgItem({id:new Date().getTime(),src:this}));
                });

                this.element.find('.file-upload-container').html(htmls.join(''));
		    }else if(value && typeof(value)=='string'){
		    	//显示记录
                this.element.find('.file-upload-container').empty().append('<div class="option-item" data-value="'+value+'" data-file-id="'+value+'"><span data-name="removeFile" class="youi-icon icon-remove" title="移除文件"></span><span class="upload-info">'+value+'</span><span class="percent"></span></div>');
            }
		},
		
		clear:function(){
			this.element.find('.file-upload-container').empty();
			this.loading = false;
		},
		
		/**
		 * 销毁组件
		 */
		destory:function(){
			this.element.removeClass('youi-field')
				.removeAttr('fieldType').removeAttr('property');
			
			this.uploader.destroy();
			this.uploader = null;
			
			$.Widget.prototype.destroy.apply(this, arguments);
		}
		
	}));

	function _buildImgItem(item){
	    var htmls = [];

	    htmls.push('<div class="option-item item-img" data-value="'+item.src+'" data-file-id="'+item.id+'">');
	    htmls.push( '<span data-name="removeFile" class="youi-icon icon-remove" title="移除文件"></span>');
	    htmls.push('<img src="'+$.youi.uploadUtils.getThumbnailSrc(item.src,50,50)+'"/>');
	    htmls.push('<span class="percent"></span></div>');
	    return htmls.join('');
	}
	
	function _calShowFileSize(size){
		
		var b = size,
			k = parseInt(b*100/1024)/100,
			m = parseInt(k*100/1024)/100,
			g = parseInt(m*100/1024)/100;
		
		if(k<=1){
			return b+'B';
		}
		
		if(m<=1){
			return k+'K';
		}
		
		if(g<=1){
			return m+'M';
		}
		
		if(g>1){
			return g+'G';
		}
	}

    function isJsonFormat(str) {
        try {
            $.parseJSON(str);
        } catch (e) {
            return false;
        }
        return true;
    }
	
})(jQuery);
