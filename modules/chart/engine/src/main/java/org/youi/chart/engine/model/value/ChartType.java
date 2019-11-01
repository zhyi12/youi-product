package org.youi.chart.engine.model.value;

/**
 * Created by zhouyi on 2019/10/31.
 */
public enum ChartType {
    Line(10,"折线图"),//
    Bar(20,"柱状图"),//
    Bar3D(21,"3D柱状图"),//
    Pie(30,"饼图"),//
    Scatter(40,"散点图"),//
    GEO(50,"地理坐标"),//
    Radar(80,"雷达图"),//
    Boxplot(81,"盒须图"),//
    Heatmap(82,"热力图"),//
    Graph(83,"关系图"),//
    Lines(84,"路径图"),//
    Sunburst(85,"旭日图"),//
    Parallel(86,"平行坐标系"),//
    Sankey(87,"桑基图"),//
    Funnel(88,"漏斗图"),//
    Gauge(89,"仪表盘"),//
    PictorialBar(90,"象形柱图");//

    private int key;

    private String text;

    ChartType(int key, String text){
        this.key = key;
        this.text = text;
    }

    public int getKey() {
        return key;
    }

    public ChartType setKey(int key) {
        this.key = key;
        return this;
    }

    public String getText() {
        return text;
    }

    public ChartType setText(String text) {
        this.text = text;
        return this;
    }
}
