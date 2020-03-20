package com.cu.project;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.cu.project.ui.Profiile.Author;
import com.cu.project.ui.Profiile.Pdfmaterial;
import com.cu.project.ui.Profiile.honorpdf;
import com.cu.project.ui.Profiile.patentpdf;
import com.cu.project.ui.Profiile.projectpdf;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Excel_download {
    private HSSFWorkbook wb;
    private Cell c;
    private HSSFSheet sheet;
    private Row row;
    private  int rowno =16;

    private Font headerFont,headerFont1,font,font1;
    private File file;
    private HSSFCellStyle cellStyle2,cellStyle3,cellStyle1,cellStyle,cellStyle4;
    public Excel_download() {
          wb = new HSSFWorkbook();
        headerFont = wb.createFont();
        headerFont1 = wb.createFont();
        font = wb.createFont();
        font1 = wb.createFont();
        cellStyle=wb.createCellStyle();
        cellStyle1=wb.createCellStyle();
        cellStyle3=wb.createCellStyle();
        cellStyle2=wb.createCellStyle();
        cellStyle4=wb.createCellStyle();
        sheet  =wb.createSheet("REPORT");




        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 22);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        headerFont1.setBold(true);
        headerFont1.setFontHeightInPoints((short) 16);
        headerFont1.setColor(IndexedColors.BLACK.getIndex());
        font.setFontHeightInPoints((short) 12);
        font.setColor(IndexedColors.BLACK.getIndex());
        font1.setFontHeightInPoints((short) 12);
//        font1.setColor(IndexedColors.BRIGHT_GREEN.getIndex());

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFont(headerFont);

        cellStyle3.setAlignment(HorizontalAlignment.LEFT);
        cellStyle3.setFont(headerFont1);

        cellStyle1.setFont(font);
        cellStyle2.setFont(font);
        cellStyle2.setWrapText(true);
        cellStyle2.setAlignment(HorizontalAlignment.LEFT);
        cellStyle2.setVerticalAlignment(VerticalAlignment.TOP);
        cellStyle2.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle2.setBorderTop(BorderStyle.MEDIUM);
        cellStyle2.setBorderRight(BorderStyle.MEDIUM);
        cellStyle2.setBorderLeft(BorderStyle.MEDIUM);

//        cellStyle2.setFillForegroundColor(IndexedColors.LIME.getIndex());
//        cellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle4.setFont(font1);
//        cellStyle4.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cellStyle4.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        cellStyle4.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cellStyle4.setWrapText(true);
        cellStyle4.setAlignment(HorizontalAlignment.LEFT);
        cellStyle4.setVerticalAlignment(VerticalAlignment.TOP);
        cellStyle4.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle4.setBorderTop(BorderStyle.MEDIUM);
        cellStyle4.setBorderRight(BorderStyle.MEDIUM);
        cellStyle4.setBorderLeft(BorderStyle.MEDIUM);

    }

    public void Publication(ArrayList<Pdfmaterial> listitems){


        row  = sheet.createRow(rowno++);
        c = row.createCell(0);
        c.setCellValue("Publication");
        c.setCellStyle(cellStyle3);
        sheet.addMergedRegion(new CellRangeAddress(rowno-1,rowno-1,0,2));

        row  = sheet.createRow(rowno);
        c = row.createCell(0);
        c.setCellValue("S.no");
        c.setCellStyle(cellStyle4);
        c = row.createCell(1);
        c.setCellValue("Title");
        c.setCellStyle(cellStyle4);
        c = row.createCell(2);
        c.setCellStyle(cellStyle4);
        c = row.createCell(3);
        c.setCellValue("Publisher");
        c.setCellStyle(cellStyle4);
        c = row.createCell(4);
        c.setCellStyle(cellStyle4);
        c = row.createCell(5);
        c.setCellValue("Date");
        c.setCellStyle(cellStyle4);
        c = row.createCell(6);
        c.setCellStyle(cellStyle4);

        c = row.createCell(7);
        c.setCellValue("URL");
        c.setCellStyle(cellStyle4);
        c = row.createCell(8);
        c.setCellStyle(cellStyle4);

        sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,1,2));
        sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,3,4));
        sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,5,6));
        sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,7,8));

        rowno++;

        for (int i=0;i<listitems.size();i++){



            Pdfmaterial obj  = listitems.get(i);
            row  = sheet.createRow(rowno);
            c = row.createCell(0);
            c.setCellValue(i+1);
            c.setCellStyle(cellStyle4);
            c = row.createCell(1);
            c.setCellValue(obj.getTitle());
            c.setCellStyle(cellStyle4);
            c = row.createCell(2);
            c.setCellStyle(cellStyle4);

            c = row.createCell(3);
            c.setCellValue(obj.getPublisher());
            c.setCellStyle(cellStyle4);
            c = row.createCell(4);
            c.setCellStyle(cellStyle4);

            c = row.createCell(5);
            c.setCellValue(obj.getDate());
            c.setCellStyle(cellStyle4);
            c = row.createCell(6);
            c.setCellStyle(cellStyle4);

            c = row.createCell(7);
            c.setCellValue(obj.getURL());
            c.setCellStyle(cellStyle4);
            c = row.createCell(8);
            c.setCellStyle(cellStyle4);
            //            sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,0,1));
            sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,1,2));
            sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,3,4));
            sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,5,6));
            sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,7,8));

            rowno++;

            row  = sheet.createRow(rowno);
            c = row.createCell(0);
            c.setCellValue("Description:- "+obj.getDes());
            c.setCellStyle(cellStyle4);
            c = row.createCell(8);
            c.setCellStyle(cellStyle4);
            row  = sheet.createRow(rowno+1);
            c = row.createCell(8);
            c.setCellStyle(cellStyle4);
            row  = sheet.createRow(rowno+2);
            for (int z=0;z<9;z++) {
                c = row.createCell(z);
                c.setCellStyle(cellStyle4);
            }

            sheet.addMergedRegion(new CellRangeAddress(rowno,rowno+2,0,8));



            rowno++;
            rowno++;
            rowno++;




            row  = sheet.createRow(rowno);

            c = row.createCell(0);
            c.setCellValue("Authors");
            c.setCellStyle(cellStyle2);
            c = row.createCell(1);
            c.setCellValue("Name");

            c.setCellStyle(cellStyle2);
            c = row.createCell(2);
            c.setCellStyle(cellStyle2);
            c = row.createCell(3);
            c.setCellValue("Email");
            c.setCellStyle(cellStyle2);
            c = row.createCell(4);
            c.setCellStyle(cellStyle2);
            c = row.createCell(5);
            c.setCellStyle(cellStyle2);
            c = row.createCell(6);
            c.setCellValue("Phone no");
            c.setCellStyle(cellStyle2);
            c = row.createCell(7);
            c.setCellStyle(cellStyle2);
            c = row.createCell(8);
            c.setCellStyle(cellStyle2);
            sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,1,2));
            sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,3,5));
            sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,6,8));
            ArrayList<Author> array  = obj.getAuthor();
            rowno++;
            for(int j=0;j<array.size();j++){
                Author author = array.get(j);
                row  = sheet.createRow(rowno);
                c = row.createCell(0);
                c.setCellValue(j+1);
                c.setCellStyle(cellStyle2);
                c = row.createCell(1);
                c.setCellValue(author.getName());
                c.setCellStyle(cellStyle2);
                c = row.createCell(2);
                c.setCellStyle(cellStyle2);

                c = row.createCell(3);
                c.setCellValue(author.getEmail());
                c.setCellStyle(cellStyle2);
                c = row.createCell(4);
                c.setCellStyle(cellStyle2);

                c = row.createCell(5);
                c.setCellStyle(cellStyle2);
                c = row.createCell(6);
                c.setCellValue(author.getPno());
                c.setCellStyle(cellStyle2);

                c = row.createCell(7);
                c.setCellStyle(cellStyle2);
                c = row.createCell(8);
                c.setCellStyle(cellStyle2);

                //            sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,0,1));
                sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,1,2));
                sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,3,5));
                sheet.addMergedRegion(new CellRangeAddress(rowno,rowno,6,8));
                rowno++;

            }










            rowno++;
        }





    }
    public void Patent(ArrayList<patentpdf> listitem){


    }
    public void Project(ArrayList<projectpdf>listitem){


    }

    public void HonorAndAward(ArrayList<honorpdf> listitem){


    }







    public   void  persanalinfo(){




        row  = sheet.createRow(0);
        c = row.createCell(0);
        c.setCellValue("YOUR FULL REPORT");
        c.setCellStyle(cellStyle);
        row  = sheet.createRow(4);
        c = row.createCell(0);
        c.setCellValue("IMAGE");
        c.setCellStyle(cellStyle);

//        row  = sheet.createRow(4);
        c = row.createCell(4);
        c.setCellValue("NAME");
        c.setCellStyle(cellStyle1);

        row  = sheet.createRow(5);
        c = row.createCell(4);
        c.setCellValue("E-Code");
        c.setCellStyle(cellStyle1);

        row  = sheet.createRow(6);
        c = row.createCell(4);
        c.setCellValue("E-Mail");
        c.setCellStyle(cellStyle1);

        row  = sheet.createRow(7);
        c = row.createCell(4);
        c.setCellValue("PHONE NO");
        c.setCellStyle(cellStyle1);

        row  = sheet.createRow(8);
        c = row.createCell(4);
        c.setCellValue("Department");
        c.setCellStyle(cellStyle1);

        row  = sheet.createRow(9);
        c = row.createCell(4);
        c.setCellValue("Quallification");
        c.setCellStyle(cellStyle1);

        row  = sheet.createRow(10);
        c = row.createCell(4);
        c.setCellValue("University");
        c.setCellStyle(cellStyle1);

        row  = sheet.createRow(11);
        c = row.createCell(4);
        c.setCellValue("Date Of Birth");
        c.setCellStyle(cellStyle1);


        row  = sheet.createRow(12);
        c = row.createCell(4);
        c.setCellValue("Date Of Joining");
        c.setCellStyle(cellStyle1);






        sheet.addMergedRegion(new CellRangeAddress(0,2,0,14));
        sheet.addMergedRegion(new CellRangeAddress(4,13,0,2));



//        for (int i = 20; i < 25; i++) {
//            row  = sheet.createRow(i );
//
//            for (int j = 10; j < 13; j++) {
//
//
//                c = row.createCell(j);
//                c.setCellValue(data[i-20][j-10].toString());
//                Log.e("tag", "excel: "+ data[i-20][j-10].toString());
//                c.setCellStyle(cellStyle2);
//
//            }
//
//        }






    }


    public void  downloadfile(){
        file  = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"60.xls");
        FileOutputStream outputStream = null;
        try{
            outputStream  = new FileOutputStream(file);
            wb.write(outputStream);
//            Toast.makeText(this,"dassd", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
