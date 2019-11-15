/**
 * Created by zhouyi on 2019/9/5.
 */
(function ($) {


    var _META_OBJECT_CLASS = 'metaObject',
        _FOLDER_CLASS = 'folder';

    //文本资源
    $.extend($.youi.resource,{
        'metaProjectDesigner.submit.confirm':$.youi.getMessage('确认保存元数据{0}.'),
    });
    /**
     * 调查项目元数据设计器
     */
    $.widget("youi.metaProjectDesigner", $.youi.abstractWidget, $.extend({}, $.youi.abstractDesigner,{

        /**
         *
         */
        options:{
            bindResize:true,
            editMetaObjectUrl:'',
            projectId:'metaProject',
        },

        /**
         *
         * @private
         */
        _initWidget:function () {
            this.treePageElem = this.element.parent('.youi-treePage');
            if(this.treePageElem.length){
                window[this.treePageElem.attr('id')+'_select'] = this._proxy('_treeNodeSelect');
            }
            this._initRefWidget();//初始化设计器关联组件（abstractDesigner）
            this._bindWidgetCommand();//组件事件代理
        },

        /**
         *
         * @param dom
         * @param options
         */
        openAddMetaPlan:function (dom,options) {
            this._openSubPage('addMetaPlan');
        },

        /**
         * 填充表单数据
         * @param record
         * @returns {{parentId: *}}
         * @private
         */
        _addMetaTaskFormRecord:function (record) {
            return {parentId:record.id,projectId:record.projectId};
        },

        _addMetaIndicatorFormRecord:function (record) {
            if("metaReport" == record.metaObjectName){
                //在报表节点上添加指标，展开指标节点
                this.modelTreeElem.tree('openPath','M_'+record.id+'/');
            }
            return {parentId:record.id,projectId:record.projectId};
        },

        /**
         *
         * @param treeNode
         * @private
         */
        _treeNodeSelect:function (treeNode) {
            if(treeNode.hasClass(_META_OBJECT_CLASS)){
                //打开对象编辑页面
                var metaObject = $.extend({parentId:treeNode.parents('.metaObject.treeNode:first').data('id')},treeNode.data());
                var pageUrl = this.options.editMetaObjectUrl+'?projectId={projectId}&metaObjectName={metaObjectName}&id={id}';
                this.treePageElem.treePage('loadPage',$.youi.recordUtils.replaceByRecord(pageUrl,metaObject));
                this.element.trigger('activeTools',{groups:['metaObject']});
            }else if(treeNode.hasClass(_FOLDER_CLASS)){
                //展示下级元数据列表
                this.element.trigger('activeTools',{groups:['folder']});
            }
        },

        /**
         * 提交元数据属性修改
         */
        submit:function () {
            //获取元数据信息
            var metaObjectCaption ='';
            $.youi.messageUtils.confirm($.youi.resourceUtils.get('metaProjectDesigner.submit.confirm',metaObjectCaption),this._proxy('_submit'));
        },

        /**
         *
         * @private
         */
        _submit:function () {

        },

        /**
         *
         * @private
         */
        _destroy:function () {

        }

    }));


})(jQuery);