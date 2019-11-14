package org.youi.xlsreport.service.impl;

import com.gwssi.xls.POIUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Component;
import org.youi.xlsreport.model.XlsReport;
import org.youi.xlsreport.service.IReportFinderDispatcher;
import org.youi.xlsreport.service.IReportFinderRuleAdapter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
@Component
public class ReportFinderDispatcherImpl implements IReportFinderDispatcher, ApplicationContextAware {

    private final static Log logger = LogFactory.getLog(ReportFinderDispatcherImpl.class);

    private List<IReportFinderRuleAdapter> reportFinderAdapters;

    private final static Pattern spacePattern = Pattern.compile("[\\s|#]+");//资质

    @Override
    public XlsReport findReportWithData(Sheet sheet) {
        return findReport(sheet,true);
    }

    @Override
    public XlsReport findReport(Sheet sheet) {
        return findReport(sheet,false);
    }

    @Override
    public XlsReport findReport(Sheet sheet,boolean readData) {

        XlsReport xlsReport = null;
        if (reportFinderAdapters != null && !reportFinderAdapters.isEmpty()) {
            for (IReportFinderRuleAdapter reportFinderAdapter : reportFinderAdapters) {
                if (xlsReport == null) {
                    xlsReport = reportFinderAdapter.parseReport(sheet);
                }
            }
        }

        // 一维指标表也不做处理
        if (xlsReport != null && !"oneDeimension".equals(xlsReport.getReportType())) {
            xlsReport.setSheetName(sheet.getSheetName());
            //主宾栏items
            Iterator<Row> rows = sheet.rowIterator();
            int mainStartRow = xlsReport.getDataArea().getStartRow();
            int mainEndRow = xlsReport.getDataArea().getEndRow();
            int mainStartCol = xlsReport.getBodyArea().getStartCol();
            int mainEndCol = xlsReport.getMainEndCol();

            int slaveCount = xlsReport.getDataArea().getEndCol()-xlsReport.getDataArea().getStartCol()+1;
            int dataCols = xlsReport.getDataArea().getEndCol() - xlsReport.getDataArea().getStartCol()+1;
            List<String> mainItemTexts = new ArrayList<>();
            String[] slaveItemTexts = new String[slaveCount];
            List<String[]> datas = new ArrayList<>();

            StringBuffer titleBuf = new StringBuffer();

            while (rows.hasNext()) {//遍历xls文件行
                Row row = rows.next();
                //标题
                if(row.getRowNum()<xlsReport.getBodyArea().getStartRow()){
                    Cell cell = row.getCell(0);
                    if(cell!=null&&titleBuf.length()==0){
                        titleBuf.append(POIUtils.format(cell,null).trim());
                    }
                }else if(rowInSlaveArea(xlsReport,row.getRowNum())){//宾栏行
                    // 记录合并单元格的值（严格的应该用sheet.getMergedRegions）
                    String mergeCellValue = "";
                    for(int col=0;col<slaveCount;col++){
                        Cell cell = row.getCell(col+xlsReport.getDataArea().getStartCol());
                        if(cell!=null){
                            String cellText = POIUtils.format(cell,null);
                            if(StringUtils.isNotBlank(cellText))
                                mergeCellValue = cellText;
                            else
                                cellText = mergeCellValue;
                            slaveItemTexts[col] = slaveItemTexts[col]!=null?slaveItemTexts[col]:"";

                            if(row.getRowNum()>xlsReport.getBodyArea().getStartRow()&& org.youi.framework.util.StringUtils.isNotEmpty(cellText)){
                                slaveItemTexts[col]+="/";
                            }
                            slaveItemTexts[col] = slaveItemTexts[col]+ org.youi.framework.util.StringUtils.trimAllWhitespace(cellText);
                        }
                    }
                }else if(row.getRowNum()>=mainStartRow&&row.getRowNum()<=mainEndRow){
                    StringBuffer rowText = new StringBuffer();
                    for(int col=mainStartCol;col<=Math.min(mainEndCol,mainStartCol+2);col++){//主栏项
                        Cell cell = row.getCell(col);
                        if(cell!=null){
                            String cellText = POIUtils.format(cell,null);
                            if(rowText.length()>0&&cellText!=null){
                                rowText.append("/");
                            }
                            rowText.append(cellText==null?"":cellText);
                        }
                    }

                    mainItemTexts.add(rowText.toString());

                    if(readData){
                        //数据区域
                        String[] rowData = new String[dataCols];
                        for(int col=0;col<dataCols;col++){
                            Cell cell = row.getCell(col+xlsReport.getDataArea().getStartCol());
                            if(cell!=null){
                                String cellText = POIUtils.format(cell,null);
                                rowData[col] = cellText;
                            }else{
                                rowData[col] = "";//
                            }
                        }
                        datas.add(rowData);
                    }
                }
            }

            if(titleBuf.length()>0){
                xlsReport.setTitle(titleBuf.toString());
            }

            addSlaveItem(xlsReport,slaveItemTexts);
            addMainItems(xlsReport,mainItemTexts);
            xlsReport.setDatas(datas.toArray(new String[datas.size()][]));
        }

        return xlsReport;
    }

    /**
     *
     * @param xlsReport
     * @param rowIndex
     * @return
     */
    private boolean rowInSlaveArea(XlsReport xlsReport,int rowIndex){
        return rowIndex>=xlsReport.getBodyArea().getStartRow()&&
                rowIndex<xlsReport.getDataArea().getStartRow();
    }

    /**
     *
     * @param xlsReport
     * @param slaveItemTexts
     */
    private void addSlaveItem(XlsReport xlsReport, String[] slaveItemTexts) {
        int index = 0;
        for(String slaveItemText:slaveItemTexts){
            xlsReport.addSlaveItem("S"+StringUtils.leftPad(String.valueOf(index++),3,"0"),slaveItemText,0);
        }
    }
    /**
     *
     * @param mainItemTexts
     */
    private void addMainItems(XlsReport xlsReport,List<String> mainItemTexts){

        int topLevelLen = -1;//顶级空格长度
        int levelLen = 0,prevLevelLen = 0;//空格长度
        int level = 0,prevLevel = 0;//层级
        int index = 0;
        Set<Integer> levelLens = new LinkedHashSet<>();
        for(String mainItemText:mainItemTexts){
            mainItemText = mainItemText.replaceAll("\\p{Zs}"," ");//中文空格转换
            Matcher matcher = spacePattern.matcher(mainItemText);

            if(matcher.find()&&matcher.start()==0){
                levelLen = matcher.group().length();
            }else{
                levelLen = 0;
            }

            if(topLevelLen==-1){
                topLevelLen = levelLen;
                level = 0;
                levelLens.clear();
            }else if(levelLen<=topLevelLen){
                topLevelLen = levelLen;
                level = 0;
                levelLens.clear();
            }

            if(levelLen>prevLevelLen){
                level = prevLevel+1;
            }else if(levelLen<prevLevelLen){
                level = findParentLevel(levelLen,levelLens);
            }
            levelLens.add(levelLen);
            //
            prevLevelLen = levelLen;
            prevLevel = level;

            xlsReport.addMainItem("M"+StringUtils.leftPad(String.valueOf(index++),3,"0"),mainItemText,level);
        }
    }

    /**
     *
     * @param levelLen
     * @param levelLens
     * @return
     */
    private int findParentLevel(int levelLen, Set<Integer> levelLens) {
        int parentLevel = 0;
        int level = 0;
        for(Integer curLevelLen:levelLens){
            if(curLevelLen>=levelLen){
                parentLevel = level;
                break;
            }
            level++;
        }
        return parentLevel;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.initAreaFinderAdapters(context);
    }

    private void initAreaFinderAdapters(ApplicationContext context) {
        if (reportFinderAdapters == null) {
            Map<String, IReportFinderRuleAdapter> beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, IReportFinderRuleAdapter.class, true, false);
            if (beans != null) {
                reportFinderAdapters = new ArrayList<IReportFinderRuleAdapter>(beans.values());
                Collections.sort(reportFinderAdapters, new OrderComparator());
            }
        }
    }
}
