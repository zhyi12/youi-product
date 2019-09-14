
(function($) {
    $.widget("youi.assignList",$.youi.abstractWidget,$.extend({},{
        /* 定义属性类型 */
        options:{
            bindResize:true,
            assignListUrl:'',
            idAttr:'id',
            height:200,
            textAttr:'text',
            extraAttr:'',
            columns:4
        },

        /**
         * 初始化方法
         * @private
         */
        _initWidget:function () {
            this.element.addClass('youi-field')
                .attr('data-field-type',this.widgetName)
                .attr('data-property',this.options.name).height(this.options.height);
            this.contentElem = this.element.find('>.assignList-content:first').selectable({
                filter:'.option-item'
            });
            var nextElem = this.element.next();
            if(nextElem.hasClass('treePage-content')){
                nextElem.remove();
            };
            this._loadBaseList();
            this._bindWidgetCommand();
        },

        _initAction:function() {
            this._on({
                'selectablestop .assignList-content':function (event,ui) {
                    var selectedElems = this.contentElem.find('.ui-selected:not(.readonly)');
                    this._selectAllEvent(selectedElems);
                    this._callGloablFunc('change',this.contentElem.find('.option-item.selected').length);
                    if(selectedElems.hasClass("remove-item") && this.options.enableConfig && typeof(this.options.enableConfig)!= 'undefined'){
                        var surveyTaskId = this.element.prev().find('[data-property=surveyTaskId]').children().val();
                        this._selectItem(surveyTaskId);
                        return false;
                    }
                    return false;
                }
            });
        },
        _selectAllEvent:function(selectedElems){
            // 对全选按钮特殊处理
            if(selectedElems.hasClass("selectAll-item")){
                this._selectAllClick(selectedElems);
                return;
            }
            if(selectedElems.length==1){
                selectedElems.toggleClass('selected');
            }else{
                selectedElems.addClass('selected');
            }
            this._changeSelectAllItem();//变更全选按钮的状态
        },

        /**
         *
         * @private
         */
        _loadBaseList:function () {
            if(this.options.assignListUrl){
                $.youi.ajaxUtils.ajax({
                    url:this.options.assignListUrl,
                    success:this._proxy('parseCommonMethod')
                });
            }
        },
        parseCommonMethod:function(result,selectedValue,extraItems){
            if(this.options.enableConfig && typeof(this.options.enableConfig)!= 'undefined'){
               this.parseListItem(result,selectedValue,extraItems);
            }else{
               this.parseList(result,selectedValue,extraItems);
            }
        },

        loadListByParams:function(params,selectedValue,extraItems){
            $.youi.ajaxUtils.ajax({
                url:this.options.assignListUrl,
                data:params,
                success:this._proxy('parseCommonMethod',selectedValue,extraItems)
            });
        },
        submitAfter:function(){
            var optionItems = this.contentElem.find('.option-item');
            optionItems.removeClass('value');
            optionItems.filter('.selected:not(.selectAll-item)').addClass('value');
        },

        /**
         *设置已下发过的值
         * @param values
         */
        setValue:function (values) {
            if(!values || !values.length){
                return;
            }
            this.contentElem.find('.option-item.checked').removeClass('checked').removeClass('value');
            if($.isArray(values)){
                var idValues = values||[],readonlyItemIds = [];
                if(typeof(values[0])=='object'){
                    var idAttr = this.options.idAttr;
                    $(values).each(function () {
                        var value = this[idAttr];
                        if(value){
                            idValues.push(value);
                            if(this.readonly)
                                readonlyItemIds.push(value);
                        }
                    });
                }
                var selectors = '.option-item[data-value='+ idValues.join('],.option-item[data-value=')+']';
                var selectedElem = this.element.find(selectors);
                selectedElem.addClass('selected value');
                var selectAllItem = this.contentElem.find(".selectAll-item");
                if(selectAllItem.length > 0) {
                    selectAllItem.after(selectedElem);
                }else {
                    selectedElem.prependTo(this.contentElem);
                }
                //if(readonlyItemIds.length){
                //    var readonlySelectors = '.option-item[data-value='+ readonlyItemIds.join('],.option-item[data-value=')+']';
                //    this.element.find(readonlySelectors).addClass('readonly');
                // }
                if(values && values.length>0){
                    this.alterAreaSatus(values);
                }
                this._changeSelectAllItem();
            }
        },
        getValue:function () {
            var values = [];
            var selectOptionItems = this.contentElem.find('.option-item.selected:not(.selectAllItem)');
            if(this.options.extraAttr){
                var that = this;
                selectOptionItems.each(function() {
                    var record = {};
                    record[that.options.idAttr] = $(this).data('value');
                    record[that.options.extraAttr] = $(this).data('extra');
                    if(that.options.enableConfig && typeof(that.options.enableConfig)!='undefined'){
                        record[that.options.textAttr] = $(this).data('text');
                        record[that.options.agencyAttr] = $(this).data('agencyattr');
                    }
                    values.push(record);
                });
            }else {
                var that = this;
                selectOptionItems.each(function() {
                    if(that.options.enableConfig && typeof(that.options.enableConfig)!='undefined'){
                        var item = {};
                        item[that.options.idAttr] = $(this).data('value');
                        item[that.options.textAttr]= $(this).data('text');
                        item[that.options.agencyAttr] = $(this).data('agencyattr');
                        values.push(item);
                    }else{
                        values.push($(this).data('value'));
                    }
                });
            }
            return values;
        },

        _selectItem:function(surveyTaskId){
            var item= [];
            var itemArray = this.contentElem.find('.option-item.selected:not(.readonly)');
            if(itemArray){
                itemArray.each(function(){
                    item.push($(this).data('agencyattr'))
                })
            }
            $.youi.ajaxUtils.ajax({
                url: this.options.updateAgencyUrl,
                type: 'POST',
                data: {'items':item.join(','),'surveyTaskId':surveyTaskId},
                success:function(result){
                    if(result.record){
                        $.youi.messageUtils.showMessage('删除勾选机构成功!');
                        window.location.reload();
                    }else{
                        $.youi.messageUtils.showError('不能删除省级机构!');
                    }
                }
            })
        },
        alterAreaSatus:function(values){
            if(!values || values.length == 0){
               return ;
            }
            var that =this;
            $(values).each(function(){
                var node = that.contentElem.children().find('[data-value='+ this.agencyAreaId+']');
                if(node.hasClass('readonly')){
                    node.removeClass('readonly');
                }
                if(this.taskStatus =='20'){
                    node.addClass('readonly');
                }else{
                    node.addClass('selected');
                }
            })
        },
        /**
         *
         */
        reset:function () {
            this.contentElem.find('.option-item.selected').removeClass('selected').removeClass('readonly').removeClass('value');
        },

        /**
         *
         */
        selectAll:function(){
            if(this.options.enableConfig && typeof(this.options.enableConfig)!='undefined'){
                this.contentElem.find('.option-item:not(".remove-item")').addClass('selected');
            }else{
                this.contentElem.find('.option-item').addClass('selected');
            }
        },

        /**
         *
         */
        unSelectAll:function(){
            this.contentElem.find('.option-item.selected:not(.readonly)').removeClass('selected');
        },

        /**
         *  生成所有可指派的项
         * @param result
         * @private
         */
        parseList:function (result,selectedValue,extraItems) {
            var htmls = [],idAttr = this.options.idAttr,textAttr=this.options.textAttr,columns = this.options.columns,extraAttr = this.options.extraAttr;
            // 增加全选功能
            var items = [];
            if(result.records&&result.records.length>0){
                items = result.records;
                htmls.push('<div style="display: block">' +
                    '<div class="option-item col-sm-12 selectAll-item" ><span class="icon-check icon-check-empty"></span>全选</div>' +
                    '</div>');
            }
            items = items.concat(extraItems||[]);
            $(items).each(function () {
                htmls.push('<div title="'+this[idAttr]+'" class="option-item col-sm-'+columns+'" data-text="'+this[textAttr]+'" data-value="'+this[idAttr]+ '" '+ (extraAttr ? ('" data-extra="'+this[extraAttr]+ '"'):'')+' ><span class="icon-check icon-check-empty"></span>'+this[textAttr]+'</div>');
            });
            this.contentElem.html(htmls.join(''));
            if(selectedValue){
                this.setValue(selectedValue);
            }
        },
        /**
         *  生成所有可指派的项
         * @param result
         * @private
         */
        parseListItem:function (result,selectedValue,extraItems) {
            var htmls = [],agencyAttr=this.options.agencyAttr,idAttr = this.options.idAttr,textAttr=this.options.textAttr,columns = this.options.columns,extraAttr = this.options.extraAttr;
            // 增加全选功能
            var items = [];
            if(result.records&&result.records.length>0){
                items = result.records;
                htmls.push('<div style="display:block">'+
                    '<div class="option-item col-sm-2 remove-item"><span class="icon-close">删除勾选机构</span></div>'+
                    '<div class="option-item col-sm-9 selectAll-item"><span class="icon-check icon-check-empty"></span>全选</div>');
            }
            items = items.concat(extraItems||[]);
            var strArray = [];
            $(items).each(function () { //获取跨级追加的机构
                if(!this[agencyAttr]){
                    strArray.push(this[idAttr]);
                }
            });
            if(strArray && strArray.length >0){
                var agencyArr = this._getAreaId(strArray.join(','));
                $(items).each(function () {
                    var retVal = '';
                    if (!agencyArr || agencyArr.length == 0) {
                        return;
                    }
                    for (var i = 0; i < agencyArr.length; i++) {
                        if (agencyArr[i].agencyId == this[idAttr]) {
                            retVal = agencyArr[i].agencyAreaId;
                            break;
                        }
                    }
                    htmls.push('<div class="option-item col-sm-' + columns + '" data-agencyAttr="' + (!this[agencyAttr] ? this[idAttr] : this[agencyAttr]) + '" data-text="' + this[textAttr] + '" data-value="' + (!this[agencyAttr] ?retVal: this[idAttr]) + '" ' + (extraAttr ? ('" data-extra="' + this[extraAttr] + '"') : '') + ' >' +
                        '<span class="icon-check icon-check-empty"></span>' + this[textAttr] + '_' + (!this[agencyAttr] ? this[idAttr] : this[agencyAttr]) + '</div>');
                })
            }else {
                $(items).each(function () {
                    htmls.push('<div class="option-item col-sm-'+columns+'" data-agencyAttr="'+this[agencyAttr]+'" data-text="'+this[textAttr]+'" data-value="'+this[idAttr]+ '" '+ (extraAttr ? ('" data-extra="'+this[extraAttr]+ '"'):'')+' >' +
                        '<span class="icon-check icon-check-empty"></span>'+this[textAttr]+'_'+(!this[agencyAttr]?this[idAttr]:this[agencyAttr])+'</div>');
                })
            }
            this.contentElem.html(htmls.join(''));
            if(selectedValue){
                this.setValue(selectedValue);
            }
        },

        getItemSize:function(){
            return this.contentElem.find('.option-item').length;
        },

        /**
         * 全选按钮单击事件
         * @private
         */
        _selectAllClick:function (elem) {
            if(elem.hasClass("selected")){
                this.unSelectAll();
            }else {
                this.selectAll();
            }
        },
        _getAreaId:function(param) {
            var areaArray = [];
            $.youi.ajaxUtils.ajax({
                url: this.options.subUrl,
                data:{'agencys':param},
                async:false,
                success:function(result){
                    if(result){
                        areaArray = result.records;
                    }
                }
            })
            return areaArray;
        },
        _changeSelectAllItem:function () {
            if(this.contentElem.find('.option-item:not(.selectAll-item):not(.selected)').length == 0)
                this.contentElem.find('.option-item.selectAll-item').addClass('selected');
            else
                this.contentElem.find('.option-item.selectAll-item').removeClass('selected');
        }
    }));
})(jQuery);