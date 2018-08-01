package com.louie.tool.hosts.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HostsUtil {
    /**
     * 读取hosts文件内容
     */
    public static List<HostVO> readHosts(String filePath, String remark) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        String[] tempArray;
        String ip;
        List<HostVO> result = new ArrayList<HostVO>();
        List<HostVO> tempList;
        String comment = "";
        String pattern = "# ?\\d+.\\d+.\\d+.\\d+.*";
        while ((line = bufferedReader.readLine()) != null) {
            tempList = new ArrayList<HostVO>();
            line = line.trim().replaceAll(" +", " ");
            //判断是否是注释行
            if (line.startsWith("#") && !line.matches(pattern)) {
                comment = line;
                continue;
            }
            if (line.startsWith("# ") && line.matches(pattern)) {
                line = line.replaceFirst(" ", "");
            }

            //解析ip 域名
            tempArray = line.split(" ");
            if (tempArray != null && tempArray.length > 0) {
                ip = null;
                for (String str : tempArray) {
                    if (StringUtils.isNotBlank(str)) {
                        if (null == ip) {
                            ip = str;
                        } else {
                            HostVO data = new HostVO();
                            data.setIp(ip);
                            data.setDomain(str);
                            data.setRemark(remark);
                            tempList.add(data);
                        }
                    }
                }
                for (HostVO map : tempList) {
                    map.setComment(comment);
                }
            }
            result.addAll(tempList);
            comment = "";
        }
        return result;
    }

    /**
     * hosts内容写入excel
     *
     * @param list
     * @param excelFilePath
     * @throws IOException
     */
    public static void writeExcel(List<HostVO> list, String excelFilePath) throws IOException {
        if (list == null || list.size() == 0) {
            return;
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        //创建标题行
        Row rowTitle = sheet.createRow(0);
        rowTitle.createCell(0).setCellValue("IP");
        rowTitle.createCell(1).setCellValue("域名");
        rowTitle.createCell(2).setCellValue("注释");
        rowTitle.createCell(3).setCellValue("备注");

        for (int i = 0; i < list.size(); i++) {
            HostVO map = list.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(map.getIp());
            row.createCell(1).setCellValue(map.getDomain());
            row.createCell(2).setCellValue(map.getComment());
            row.createCell(3).setCellValue(map.getRemark());
        }

        FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();
    }

    /**
     * 读取excel文件内容
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<HostVO> readExcel(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        List<HostVO> result = new ArrayList<HostVO>();
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            HostVO item = new HostVO();
            item.setIp(row.getCell(0).getStringCellValue());
            item.setDomain(row.getCell(1).getStringCellValue());
            item.setComment(row.getCell(2).getStringCellValue());
            item.setRemark(row.getCell(3).getStringCellValue());
            result.add(item);
        }
        return result;
    }

    /**
     * 内容写入excel
     * @param list
     * @param filePath
     * @throws IOException
     */
    public static void writeHosts(List<HostVO> list, String filePath) throws IOException {
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
        file.createNewFile();
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(file));
        for(HostVO item:list){
            bufferedWriter.write(item.toString());
            //bufferedWriter.newLine();
            bufferedWriter.write("\n");
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
