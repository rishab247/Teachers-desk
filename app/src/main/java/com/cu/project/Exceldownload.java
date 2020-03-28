package com.cu.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import java.util.Set;

import static android.content.ContentValues.TAG;

public class Exceldownload {
    private static final int STORAGE_CODE = 1000;
     Set<String> listofuser=null;
    int sizeofeachuser,sizeofpub,sizeofpro,sizeofpat,sizeofhon;
    int sizeofeachuserrow,sizeofpubrow,sizeofprorow,sizeofpatrow,sizeofhonrow;


    String date;

    private Set<String> getlistofuser( JSONArray arr){
        listofuser = new HashSet <String>();

        for(int i=0 ;i< arr.length();i++ ){
            try {
                listofuser.add(arr.getJSONArray(i).getString(1));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        return listofuser;
    }
    private  void  getsizeofeachuser(String Euid,JSONObject jsonObj){
        sizeofeachuser=0;
        sizeofpro=0;
        sizeofpub=0;
        sizeofpat=0;
        sizeofhon=0;

        try {
            JSONArray arr = (JSONArray) jsonObj.get("Publication");

            for(int i=0 ;i< arr.length();i++ ){
                try {

                    if(arr.getJSONArray(i).getString(1).equals(Euid))
                    {sizeofeachuser++;
                        sizeofpub++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }}


            arr = (JSONArray) jsonObj.get("Project");

            for(int i=0 ;i< arr.length();i++ ){
                try {

                    if(arr.getJSONArray(i).getString(1).equals(Euid))
                    {sizeofeachuser++;
                        sizeofpro++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }}



            arr = (JSONArray) jsonObj.get("Patent");

            for(int i=0 ;i< arr.length();i++ ){
                try {

                    if(arr.getJSONArray(i).getString(1).equals(Euid))
                    {sizeofeachuser++;
                        sizeofpat++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }}



            arr = (JSONArray) jsonObj.get("Honors_and_Award");

            for(int i=0 ;i< arr.length();i++ ){
                try {

                    if(arr.getJSONArray(i).getString(1).equals(Euid))
                    {sizeofeachuser++;
                        sizeofhon++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }}




        } catch (JSONException e) {
            e.printStackTrace();
        }




    }




    public   void  downloadexcel( String str){

        JSONObject jsonObj=null;

        DateFormat simple = new SimpleDateFormat("dd/MMM/yyyy");

        HSSFWorkbook wb = new HSSFWorkbook();
        Cell c = null;


        Font headerFont = wb.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 22);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setColor(IndexedColors.BLACK.getIndex());

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFont(headerFont);
//        HSSFCellStyle cellStyle1 = wb.createCellStyle();
//        cellStyle1.setFont(font);
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle2.setFont(font);
        HSSFCellStyle cellStyle1 = wb.createCellStyle();
        cellStyle1.setAlignment(HorizontalAlignment.LEFT);
        cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

        cellStyle1.setFont(font);

        HSSFSheet sheet  =wb.createSheet("REPORT");
        Row row  = sheet.createRow(0);
        c = row.createCell(0);
        c.setCellValue("FULL REPORT");
        c.setCellStyle(cellStyle);
        sheet.addMergedRegion(new CellRangeAddress(0,2,0,21));
//        row  = sheet.createRow(6);
        cellStyle2.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle2.setBorderTop(BorderStyle.MEDIUM);
        cellStyle2.setBorderRight(BorderStyle.MEDIUM);
        cellStyle2.setBorderLeft(BorderStyle.MEDIUM);

        row  = sheet.createRow(5);
        c = row.createCell(0);
        c.setCellValue("S.No");
        c.setCellStyle(cellStyle2);
        c = row.createCell(1);
        c.setCellValue("Emp name");
        c.setCellStyle(cellStyle2);
        c = row.createCell(2);
        c.setCellValue("Ecode");
        c.setCellStyle(cellStyle2);
        c = row.createCell(3);
        c.setCellValue("EMAIL");
        c.setCellStyle(cellStyle2);
        c = row.createCell(4);
        c.setCellValue("Phone no");
        c.setCellStyle(cellStyle2);
        c = row.createCell(5);
        c.setCellValue("Department");
        c.setCellStyle(cellStyle2);
        c = row.createCell(6);
        c.setCellValue("DOJ");
        c.setCellStyle(cellStyle2);
        c = row.createCell(7);
        c.setCellValue("Qualification");
        c.setCellStyle(cellStyle2);
        c = row.createCell(8);
        c.setCellValue("University");
        c.setCellStyle(cellStyle2);
        c = row.createCell(9);
        c.setCellValue("Type");
        c.setCellStyle(cellStyle2);
        c = row.createCell(10);
        c.setCellValue("Title");
        c.setCellStyle(cellStyle2);
        c = row.createCell(11);
        c.setCellValue("date");
        c.setCellStyle(cellStyle2);
        c = row.createCell(12);
        c.setCellValue("Publication_type");
        c.setCellStyle(cellStyle2);
        c = row.createCell(13);
        c.setCellValue("Issuer");
        c.setCellStyle(cellStyle2);
        c = row.createCell(14);
        c.setCellValue("URL");
        c.setCellStyle(cellStyle2);
        c = row.createCell(15);
        c.setCellValue("Application_no");
        c.setCellStyle(cellStyle2);
        c = row.createCell(16);
        c.setCellValue("Decription");
        c.setCellStyle(cellStyle2);
        c = row.createCell(17);
        c.setCellValue("Author name");
        c.setCellStyle(cellStyle2);
        c = row.createCell(18);
        c.setCellValue("Author Email");
        c.setCellStyle(cellStyle2);
        c = row.createCell(19);
        c.setCellValue("Author Phone no");
        c.setCellStyle(cellStyle2);
        sizeofeachuserrow  = 6;
        sizeofhonrow  = 0;
        sizeofpatrow  = 0;
        sizeofprorow  = 0;
        sizeofpubrow  = 0;

        try {
            jsonObj = new JSONObject(str);
            JSONArray personaldataarr = (JSONArray) jsonObj.get("profile");
            listofuser = getlistofuser(personaldataarr);
            for (int i=0;i<listofuser.size();i++){
                JSONArray personaldata = (JSONArray) personaldataarr.get(i);
                getsizeofeachuser(listofuser.toArray()[i].toString(),jsonObj);
                if(sizeofeachuser==0)
                    sizeofeachuser++;
                Log.e("TAG", "test: "+listofuser.toArray()[i].toString()+"  "+ sizeofeachuser  );
                Log.e("TAG", "test: "+listofuser.toArray()[i].toString()+"  "+ sizeofpub  );
                Log.e("TAG", "test: "+listofuser.toArray()[i].toString()+"  "+ sizeofpat  );
                Log.e("TAG", "test: "+listofuser.toArray()[i].toString()+"  "+ sizeofpro  );
                Log.e("TAG", "test: "+listofuser.toArray()[i].toString()+"  "+ sizeofhon  );



                int temp = sizeofeachuser+sizeofeachuserrow-1;
                int temp1 = sizeofpub+sizeofeachuserrow-1;
                int temp2 = sizeofpat+temp1 ;
                int temp3 = sizeofpro+temp2 ;
                int temp4 = sizeofhon+temp3 ;
                row  = sheet.createRow(sizeofeachuserrow);
                c = row.createCell(0);
                c.setCellValue(i+1);

                c.setCellStyle(cellStyle1);
                if((temp-sizeofeachuserrow)>1)
                    sheet.addMergedRegion(new CellRangeAddress(sizeofeachuserrow,temp,0, 0));
                c = row.createCell(1);
                c.setCellValue(personaldata.getString(0));

                c.setCellStyle(cellStyle1);
                if((temp-sizeofeachuserrow)>1)
                    sheet.addMergedRegion(new CellRangeAddress(sizeofeachuserrow,temp,1, 1));
                c = row.createCell(2);
                c.setCellValue(personaldata.getString(1));

                c.setCellStyle(cellStyle1);
                if((temp-sizeofeachuserrow)>1)
                    sheet.addMergedRegion(new CellRangeAddress(sizeofeachuserrow,temp,2, 2));
                c = row.createCell(3);
                c.setCellValue(personaldata.getString(2));
                c.setCellStyle(cellStyle1);
                if((temp-sizeofeachuserrow)>1)
                    sheet.addMergedRegion(new CellRangeAddress(sizeofeachuserrow,temp,3, 3));
                c = row.createCell(4);
                c.setCellValue(personaldata.getString(3));
                c.setCellStyle(cellStyle1);
                if((temp-sizeofeachuserrow)>1)
                    sheet.addMergedRegion(new CellRangeAddress(sizeofeachuserrow,temp,4, 4));
                c = row.createCell(5);
                c.setCellValue(personaldata.getString(4));
                c.setCellStyle(cellStyle1);
                if((temp-sizeofeachuserrow)>1)
                    sheet.addMergedRegion(new CellRangeAddress(sizeofeachuserrow,temp,5, 5));
                c = row.createCell(6);
                c.setCellValue(personaldata.getString(5));
                c.setCellStyle(cellStyle1);
                if((temp-sizeofeachuserrow)>1)
                    sheet.addMergedRegion(new CellRangeAddress(sizeofeachuserrow,temp,6, 6));
                c = row.createCell(7);
                c.setCellValue(personaldata.getString(6));
                c.setCellStyle(cellStyle1);
                if((temp-sizeofeachuserrow)>1)
                    sheet.addMergedRegion(new CellRangeAddress(sizeofeachuserrow,temp,7, 7));
                c = row.createCell(8);
                c.setCellValue(personaldata.getString(7));
                c.setCellStyle(cellStyle1 );
                if((temp-sizeofeachuserrow)>1)
                    sheet.addMergedRegion(new CellRangeAddress(sizeofeachuserrow,temp,8, 8));
                Log.e("TAG", "test:123 "+"  "+ temp4+"  "+ sizeofeachuserrow+"  "+sizeofpubrow);
                String prevtitle="";
                String prestitle="";
                int mergestart =0;
                int mergeend =0;
                try{
                    JSONArray Publicationdataarr = (JSONArray) jsonObj.get("Publication");
                    if(((temp-sizeofeachuserrow)>1)&&(sizeofpub)>0) {
                        c = row.createCell(9);
                        c.setCellValue("Publication");
                        c.setCellStyle(cellStyle1);
                        if((temp1-sizeofeachuserrow)>1)
                            sheet.addMergedRegion(new CellRangeAddress(sizeofeachuserrow , temp1 , 9 , 9));

                        for(int z=sizeofeachuserrow;z<sizeofeachuserrow+sizeofpub ;z++){
                            JSONArray Typedata = (JSONArray) Publicationdataarr.get(sizeofpubrow++);
                            row.setRowNum(z );



                            c = row.createCell(10);
                            c.setCellValue(Typedata.getString(2));
                            c.setCellStyle(cellStyle1);
                            try{
                                Date result = new Date(Long.parseLong(Typedata.getString(4)));
                                date = simple.format(result);}
                            catch (Exception e){
                                date = Typedata.getString(4);
                            }
                            c = row.createCell(11);
                            c.setCellValue(date);
                            c.setCellStyle(cellStyle1);

                            c = row.createCell(12);
                            c.setCellValue(Typedata.getString(5));
                            c.setCellStyle(cellStyle1);

                            c = row.createCell(13);
                            c.setCellValue(Typedata.getString(3));
                            c.setCellStyle(cellStyle1);

                            c = row.createCell(14);
                            c.setCellValue(Typedata.getString(7));
                            c.setCellStyle(cellStyle1);

                            c = row.createCell(16);
                            c.setCellValue(Typedata.getString(6));
                            c.setCellStyle(cellStyle1);

                            c = row.createCell(17);
                            c.setCellValue(Typedata.getString(8));
                            c.setCellStyle(cellStyle1);

                            c = row.createCell(18);
                            c.setCellValue(Typedata.getString(9));
                            c.setCellStyle(cellStyle1);

                            c = row.createCell(19);
                            c.setCellValue(Typedata.getString(10));
                            c.setCellStyle(cellStyle1);


                            prevtitle = prestitle;
                            prestitle = Typedata.getString(2);
                            if(prevtitle.equals(prestitle)){
                                mergeend++;
                            }
                            else{
                                Log.e( "merge123" ,mergestart+  "   "+mergeend+"   "+(mergeend-mergestart)+"  "+z );

                                if((mergeend-mergestart)>0){
                                    for(int y=10;y<17;y++)
                                        sheet.addMergedRegion(new CellRangeAddress(mergestart,mergeend,y, y));

                                }


                                mergestart=z;
                                mergeend=z;
                            }


                        }
                        if((mergeend-mergestart)>0){
                            for(int y=10;y<17;y++)
                                sheet.addMergedRegion(new CellRangeAddress(mergestart,mergeend,y, y));

                        }


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    JSONArray Patentdataarr = (JSONArray) jsonObj.get("Patent");
                    if(((temp-sizeofeachuserrow)>1)&&(sizeofpat)>0) {
                        row  = sheet.createRow(temp1 +1);

                        c = row.createCell(9);
                        c.setCellValue("Patent");
                        c.setCellStyle(cellStyle1);
                        if((temp2-temp1 )>1)

                            sheet.addMergedRegion(new CellRangeAddress(temp1 +1, temp2 , 9 , 9));
                        prevtitle="";
                        prestitle="";
                        mergestart =0;
                        mergeend =0;
                        for(int z=temp1 +1;z<temp1 +1+sizeofpat ;z++){
                            JSONArray Typedata   = (JSONArray) Patentdataarr.get(sizeofpatrow++);
                            row.setRowNum(z );



                            c = row.createCell(10);
                            c.setCellValue(Typedata.getString(2));
                            c.setCellStyle(cellStyle1);
                            try{
                                Date result = new Date(Long.parseLong(Typedata.getString(5)));
                                date = simple.format(result);}
                            catch (Exception e){
                                date = Typedata.getString(5);
                            }
                            c = row.createCell(11);
                            c.setCellValue(date);
                            c.setCellStyle(cellStyle1);

                            c = row.createCell(13);
                            c.setCellValue(Typedata.getString(3));
                            c.setCellStyle(cellStyle1);

                            c = row.createCell(14);
                            c.setCellValue(Typedata.getString(7));
                            c.setCellStyle(cellStyle1);

                            c = row.createCell(15);
                            c.setCellValue(Typedata.getString(4));
                            c.setCellStyle(cellStyle1);

                            c = row.createCell(16);
                            c.setCellValue(Typedata.getString(6));
                            c.setCellStyle(cellStyle1);

                            c = row.createCell(17);
                            c.setCellValue(Typedata.getString(8));
                            c.setCellStyle(cellStyle1);
                            c = row.createCell(18);
                            c.setCellValue(Typedata.getString(9));
                            c.setCellStyle(cellStyle1);
                            c = row.createCell(19);
                            c.setCellValue(Typedata.getString(10));
                            c.setCellStyle(cellStyle1);



                            prevtitle = prestitle;
                            prestitle = Typedata.getString(2);
                            if(!prevtitle.equals(prestitle))
                            {
                                Log.e( "merge123" ,mergestart+  "   "+mergeend+"   "+(mergeend-mergestart)+"  "+z );

                                if((mergeend-mergestart)>0){
                                    for(int y=10;y<17;y++)
                                        sheet.addMergedRegion(new CellRangeAddress(mergestart,mergeend,y, y));

                                }


                                mergestart=z;
                                mergeend=z;
                            }else{
                                mergeend++;

                            }


                        }

                        if((mergeend-mergestart)>0){
                            for(int y=10;y<17;y++)
                                sheet.addMergedRegion(new CellRangeAddress(mergestart,mergeend,y, y));

                        }


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    JSONArray projectdataarr = (JSONArray) jsonObj.get("Project");

                    if((((temp-sizeofeachuserrow)>1)&&(sizeofpro)>0)) {
                        row  = sheet.createRow(temp2 +1);

                        c = row.createCell(9);
                        c.setCellValue("Project");
                        c.setCellStyle(cellStyle1);
                        if((temp3-temp2)>1)

                            sheet.addMergedRegion(new CellRangeAddress(temp2 +1, temp3 , 9 , 9));




                        prevtitle="";
                        prestitle="";
                        mergestart =0;
                        mergeend =0;
                        for(int z=temp2 +1;z<temp2 +1+sizeofpro ;z++){
                            JSONArray Typedata   = (JSONArray) projectdataarr .get(sizeofprorow++);
                            row.setRowNum(z );
                            c = row.createCell(10);
                            c.setCellValue(Typedata.getString(2));
                            c.setCellStyle(cellStyle1);
                            try{
                                Date result = new Date(Long.parseLong(Typedata.getString(3)));
                                date = simple.format(result);}
                            catch (Exception e){
                                date = Typedata.getString(3);
                            }
                            c = row.createCell(11);
                            c.setCellValue(date);
                            c.setCellStyle(cellStyle1);
                            c = row.createCell(14);
                            c.setCellValue(Typedata.getString(5));
                            c.setCellStyle(cellStyle1);
                            c = row.createCell(16);
                            c.setCellValue(Typedata.getString(4));
                            c.setCellStyle(cellStyle1);
                            c = row.createCell(17);
                            c.setCellValue(Typedata.getString(6));
                            c.setCellStyle(cellStyle1);
                            c = row.createCell(18);
                            c.setCellValue(Typedata.getString(7));
                            c.setCellStyle(cellStyle1);
                            c = row.createCell(19);
                            c.setCellValue(Typedata.getString(8));
                            c.setCellStyle(cellStyle1);
                            prevtitle = prestitle;
                            prestitle = Typedata.getString(2);
                            if(!prevtitle.equals(prestitle))
                            {
                                Log.e( "merge123" ,mergestart+  "   "+mergeend+"   "+(mergeend-mergestart)+"  "+z );

                                if((mergeend-mergestart)>0){
                                    for(int y=10;y<17;y++)
                                        sheet.addMergedRegion(new CellRangeAddress(mergestart,mergeend,y, y));

                                }


                                mergestart=z;
                                mergeend=z;
                            }else{
                                mergeend++;

                            }


                        }

                        if((mergeend-mergestart)>0){
                            for(int y=10;y<17;y++)
                                sheet.addMergedRegion(new CellRangeAddress(mergestart,mergeend,y, y));

                        }







                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    JSONArray honorandawarddataarr = (JSONArray) jsonObj.get("Honors_and_Award");
                    if((((temp-sizeofeachuserrow)>1)&&(sizeofhon)>0)) {
                        row  = sheet.createRow(temp3 +1);

                        c = row.createCell(9);
                        c.setCellValue("Honor and Award");
                        c.setCellStyle(cellStyle1);
                        if((temp4-temp3 )>1)

                            sheet.addMergedRegion(new CellRangeAddress(temp3 +1, temp4 , 9 , 9));

                        for(int z=temp3 +1;z<temp3 +1+sizeofhon ;z++){
                            JSONArray Typedata   = (JSONArray) honorandawarddataarr .get(sizeofhonrow++);
                            row.setRowNum(z );
                            c = row.createCell(10);
                            c.setCellValue(Typedata.getString(2));
                            c.setCellStyle(cellStyle1);
                            try{
                                Date result = new Date(Long.parseLong(Typedata.getString(4)));
                                date = simple.format(result);}
                            catch (Exception e){
                                date = Typedata.getString(4);
                            }
                            c = row.createCell(11);
                            c.setCellValue(date);
                            c.setCellStyle(cellStyle1);
                            c = row.createCell(13);
                            c.setCellValue(Typedata.getString(3));
                            c.setCellStyle(cellStyle1);
                            c = row.createCell(16);
                            c.setCellValue(Typedata.getString(5));
                            c.setCellStyle(cellStyle1);

                        }
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }


                sizeofeachuserrow+=sizeofeachuser;


            }



        } catch (JSONException e) {
            e.printStackTrace();
        }




        Date currentTime = Calendar.getInstance().getTime();

        File file  = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),currentTime.toString()+".xls");
        FileOutputStream outputStream = null;
        try{
            outputStream  = new FileOutputStream(file);
            wb.write(outputStream);
         }catch (Exception e){
            e.printStackTrace();
        }



    }


}
