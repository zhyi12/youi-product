/*!
 * youi JavaScript Library v3.0.0
 * 
 *
 * Copyright 2015, zhyi_12
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Date: 2015-7-2
 */

(function($) {
	var _log = $.youi.log;

	// $.youi.uploadUtils = $.extend($.youi.uploadUtils,{
	//     getImageSrc:function(refPath){
	//         return $.youi.serverConfig.contextPath+'upload/download/'+refPath+'.html';
	//     },
    //
	//     getThumbnailSrc:function(refPath,width,height){
	//         width  = width||30;
	//         height = height||30;
     //        return $.youi.serverConfig.contextPath+'img/thumbnail/'+width+'/'+height+'/'+refPath+'.html';
     //    }
	// });
	/**
	 * fieldWebuploader组件
	 */
	$.widget("youi.fieldWebuploader",$.youi.abstractWidget,$.extend({},$.youi.field,{
		
		/**
		 * 参数配置
		 */
		options:{
			uploadUrl:'fileServer/upload.json',
			flash_swf_url:'asserts/js/webuploader/Uploader.swf',
			serverFileCaption:'在服务器中选择',
            md5CheckSize:15,//15M
            // serverFileSrc:'',searchServerFileSrc:''
			maxSize:'1000mb',
			limits:10,//最大允许上传的文件个数
			mineTypeCaption:'Images',
            mimeTypes:'image/*',
			mineTypeExtensions:'gif,jpg,jpeg,bmp,png'
		},
		
		_initField:function() {
            var filters = {max_file_size : this.options.maxSize},
                headers = {'X-CSRF-TOKEN':$.youi.serverConfig.csrfToken};
            this.uploader = WebUploader.create({
                headers:headers,
                // 选完文件后，是否自动上传。
                auto: false,
                // swf文件路径
                swf: $.youi.serverConfig.contextPath + this.options.flash_swf_url,

                // 文件接收服务端。
                server: $.youi.serverConfig.contextPath+this.options.uploadUrl,

                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: '#'+this.options.id+'_pickfiles',

                // 只允许选择图片文件。
                accept: {
                    title: this.options.mineTypeCaption,
                    //extensions: this.options.mineTypeExtensions,
                    mimeTypes: this.options.mimeTypes
                }
            });
			//文件加入队列事件
            this.uploader.on('fileQueued', this._proxy('_fileQueued'));
			//文件发送前事件
            this.uploader.on('uploadBeforeSend', this._proxy('_uploadBeforeSend'));
			//文件发送进度事件
            this.uploader.on('uploadProgress', this._proxy('_uploadProgress'));//file p
            //文件上传成功事件
            this.uploader.on('uploadSuccess',this._proxy('_uploadSuccess') );//file, response
            //文件上传失败事件
            this.uploader.on('uploadError', this._proxy('_uploadError'));
            //文件上传完成事件
            this.uploader.on('uploadComplete', this._proxy('_uploadComplete'));
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

        /**
		 * 启动上传
         * @private
         */
        _startUpload:function () {
            var files = this.uploader.getFiles();

            for(var i=0;i<files.length;i++){
                this.uploader.md5File(files[i],0,parseInt(this.options.md5CheckSize)*1024*1024)
					.then(this._proxy('_startUploadFile',files[i]));
			}
        },

        /**
		 * 文件加入队列
         * @param file
         * @private
         */
		_fileQueued:function (file) {
			this._addFile(file);

        },

        _startUploadFile:function (md5,file) {
			//上传未上传的文件
			if(!this.element.find('[data-file-id='+file.id+']').data('value')){
                file.md5Path = md5+'@'+file.size;
                this.uploader.upload(file);
			}
        },

        _uploadBeforeSend:function (object,data,headers) {
            data.md5Path = object.file.md5Path;
        },

        _uploadProgress:function(file,percent){
            this.element.find('.file-upload-container [data-file-id='+file.id+'] .percent').html('('+percent + '%)');
        },

        _uploadSuccess:function (file,response) {
        	this._uploadProgress(file,100);
            var filePath = response.message.info;

            var fileElem = this.element.find('.file-upload-container [data-file-id='+file.id+']');
            fileElem.removeClass('preupload').attr('data-value',filePath);
            // if(this.options.showImage){
            //     var infoElem = fileElem.find('.upload-info')
            //     $('<img src="'+$.youi.uploadUtils.getThumbnailSrc(filePath,50,50)+'"/>').insertAfter(infoElem);
            //     infoElem.remove();
            // }
            //回调函数
            this._callGloablFunc('afterFileUpload', filePath);
        },

        _uploadError:function (file) {
			
        },

        _uploadComplete:function (file) {
			this._disabledUploadButton();
        },

        _defaultHtmls:function(){
            var htmls = [];

            htmls.push('<div class="btn-group btn-group-sm">');
            htmls.push('<span class="btn btn-default" id="'+this.options.id+'_pickfiles">选择文件</span><span class="btn btn-primary disabled" data-command="fieldCommand" data-name="startUpload" >上传文件</span>');

            htmls.push('</div>');
            htmls.push('<div class="file-upload-container"></div>');

            this.element.append(htmls.join(''));
        },

        _addFile:function(file){
            var showFileSize = _calShowFileSize(file.size)||'';
            this.element.find('.file-upload-container').append('<div class="option-item preupload" data-file-id="'+file.id+'"><span data-name="removeFile" class="youi-icon icon-remove" title="移除文件"></span><span class="upload-info">'+file.name+' '+showFileSize+'</span><span class="percent"></span></div>');
            this.element.find('.disabled').removeClass('disabled');
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

	}));


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
