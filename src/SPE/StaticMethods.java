package SPE;

import SPE.Classes.ChannelsValues;
import SPE.Classes.Date;
import SPE.Classes.SpeMetaData;
import SPE.Classes.Time;

public class StaticMethods {
    public static final Integer CORR_YEAR = 2000;

    static SpeMetaData getSpeHead(String[] head){
        Date speDate = convert2Date(head[0]);
        Time speTime = convert2Time(head[1]);
        float liveTime = Float.parseFloat(head[2]);
        float realTime = Float.parseFloat(head[3]);
        int speChannels = Integer.parseInt(head[4]);
        SpeMetaData spectrumHead = new SpeMetaData(speDate,speTime,liveTime,realTime,speChannels);
        return spectrumHead;
    }

    public static void printChannelsArr(ChannelsValues[] arr){
        for (ChannelsValues i : arr) {
            System.out.println(i);
        }
    }

    static ChannelsValues[] getChnValues(String[] speVal, int speChannels){
        ChannelsValues[] cChnVal = new ChannelsValues[speChannels];
        for(int i = 0; i<speVal.length;i++){
            cChnVal[i] = convert2ChnValues(String.valueOf(speVal[i]));
        }
        return cChnVal;
    }

    private static Time convert2Time(String str) {
        String[] sTime = str.split(":");
        String[] sTimeSecondsAmPm = sTime[2].split(" ");
        if (sTimeSecondsAmPm.length==1){
            sTimeSecondsAmPm = addAMPM(sTimeSecondsAmPm[0], Integer.parseInt(sTime[0]));
        }
        Time time = new Time(Integer.parseInt(sTime[0])
                , Integer.parseInt(sTime[1])
                , Integer.parseInt(sTimeSecondsAmPm[0])
                ,sTimeSecondsAmPm[1]);
        return time;
    }

    private static Date convert2Date(String str) {
        String[] sDate = str.split("-");
        sDate[2]= isYearCorrect(sDate[2]);
        Date date = new Date(Integer.parseInt(sDate[0])
                , Integer.parseInt(sDate[1])
                , Integer.parseInt(sDate[2]));
        return date;
    }

    private static ChannelsValues convert2ChnValues(String str) {
        String[] sChnVal = str.split("\t");
        ChannelsValues chnVal = new ChannelsValues(Integer.parseInt(sChnVal[0])
                , Integer.parseInt(sChnVal[1]));
        return chnVal;
    }

    private static String[] addAMPM(String str, int hours){
        String[] addAMPM = new String[2];
        addAMPM[0] = str;
        if (hours<12){
            addAMPM[1] = "AM";
        }else {
            addAMPM[1] = "PM";
        }
        return addAMPM;
    }

    private static String isYearCorrect(String year){
        int iYear = Integer.parseInt(year);
        if (iYear < 100){
            iYear += CORR_YEAR;
            return String.valueOf(iYear);
        }else {
            return year;
        }
    }
}
