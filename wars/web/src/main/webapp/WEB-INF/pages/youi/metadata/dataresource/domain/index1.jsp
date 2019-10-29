<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:script src="/asserts/js/lib/jsplumb.min.js"/>
<style>
    .item{
        position: absolute;
        width:50px;
        height: 50px;
        border: 1px solid #ddd;
    }
</style>
<youi:page caption="主题库">
    <div id="diagramContainer">
        <div id="item_left" class="item"></div>
        <div id="item_right" class="item" style="left:50px;"></div>
        <div id="item_right1" class="item" style="left:150px;"></div>
    </div>

    <script>
        /* global jsPlumb */
        jsPlumb.ready(function () {

            var plumb = jsPlumb.getInstance({
                PaintStyle: { strokeWidth: 1 },
                Anchors: [["Left","Right","Bottom"], ["Top","Bottom"]]});

            plumb.connect({
                source: 'item_left',
                target: 'item_right',
                Container:$('#diagramContainer')[0],
                connector: [ "Flowchart",
                    {
                        cornerRadius: 3,
                        stub:16
                    }
                ],
                endpoint: 'Blank',
                paintStyle: { stroke: 'lightgray', strokeWidth: 3 },
                endpointStyle: { fill: 'lightgray', outlineStroke: 'darkgray', outlineWidth: 2 },
                overlays: [ ['Arrow', { width: 12, length: 12, location: 1 }] ]
            })

            plumb.connect({
                source: 'item_left',
                target: 'item_right1',
                Container:$('#diagramContainer')[0],
                connector: [ "Flowchart",
                    {
                        cornerRadius: 3,
                        stub:16
                    }
                ],
                endpoint: 'Blank',
                paintStyle: { stroke: 'lightgray', strokeWidth: 3 },
                endpointStyle: { fill: 'lightgray', outlineStroke: 'darkgray', outlineWidth: 2 },
                overlays: [ ['Arrow', { width: 12, length: 12, location: 1 }] ]
            })

            plumb.draggable('item_left')
            plumb.draggable('item_right')
            plumb.draggable('item_right1')
        });

    </script>
</youi:page>