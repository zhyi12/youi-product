<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>chart</title>

    <script type="application/javascript" src="../asserts/js/echarts/echarts.min.js"></script>
</head>
<body>
    <div id="chart" style="width:100%;height:500px;">

    </div>

    <script>
        window.onload = function () {
            var myChart = echarts.init(document.getElementById('chart'));
            myChart.setOption(#{option});
        }
    </script>
</body>
</html>