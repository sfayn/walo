package controller.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author YOU$$EF
 */
public class Helper {

    public static void main(String[] args) {
        /*
         * Date d = new Date(90, 5, 30);
         * System.out.println(dateToStrG(d));
         * System.out.println(dateToStrH(d));
         * System.out.println(""+dateToStrArG(dateGrToH(d)));
         * System.out.println(dateToStrArG(d));
         * System.out.println(dateToStrArH(d));
         * System.out.println(dateToStrG(d));
         * System.out.println(dateToStrH(d));
         * System.out.println(dateHToStrH(dateGrToH(d)));
         * System.out.println(dateHToStrArH(dateGrToH(d)));
         */
    }

    /*
     * @param date: Date Grégorian
     * @return String: Date Gregorian
     */
    public static String dateToStrG(Date date) {
        if(date == null) {
            return null;
        }
        SimpleDateFormat d = new SimpleDateFormat("dd");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        String[] mois = {
            "Janvier",
            "Février",
            "Mars",
            "Avril",
            "Mai",
            "Juin",
            "Juillet",
            "Août",
            "Séptembre",
            "Octobre",
            "Novembre",
            "Décembre"
        };
        String result;
        result = "" + int2str(Integer.parseInt(d.format(date)));
        result += " " + mois[Integer.parseInt(m.format(date)) - 1];
        result += " " + int2str(Integer.parseInt(y.format(date)));
        return result;
    }

    /*
     * @param date: Date Héjirian
     * @return String: Date Héjirian
     */
    public static String dateHToStrH(Date date) {
        if(date == null) {
            return null;
        }
        SimpleDateFormat d = new SimpleDateFormat("dd");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        String[] mois = {"Muharram", "Safar", "Rabi'ul Awwal",
            "Rabi'ul Tani", "Jumadal Ula", "Jumadal Tania", "Rajab",
            "Sha'ban", "Ramadan", "Shawwal", "Dhul Qa'ada", "Dhul Hijja"};

        String result;
        result = "" + int2str(Integer.parseInt(d.format(date)));
        result += " " + mois[Integer.parseInt(m.format(date)) - 1];
        result += " " + int2str(Integer.parseInt(y.format(date)));
        return result;
    }

    /*
     * @param date: Date Héjirian
     * @return String: Date Héjirian Ar
     */
    public static String dateHToStrArH(Date date) {
        if(date == null) {
            return null;
        }
        SimpleDateFormat d = new SimpleDateFormat("dd");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        String[] mois = {
            "محرم",
            "صفر",
            "ربيع الأول",
            "ربيع الثاني",
            "جمادى الأولى",
            "جمادى الثانية",
            "رجب",
            "شعبان",
            "رمضان",
            "شوال",
            "ذو القعدة",
            "ذو الحجة"
        };

        String result;
        result = "" + int2strAr(Integer.parseInt(d.format(date)));
        result += " " + mois[Integer.parseInt(m.format(date)) - 1];
        result += " " + int2strAr(Integer.parseInt(y.format(date)));
        return result;
    }

    public static String dateToStrArG(Date date) {
        if(date == null) {
            return null;
        }
        SimpleDateFormat d = new SimpleDateFormat("dd");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        String[] mois = {
            "يناير",
            "فبراير",
            "مارس",
            "ابريل",
            "ماي",
            "يونيو",
            "يوليوز",
            "غشت",
            "شتنبر",
            "أكتوبر",
            "نونبر",
            "دجنبر"
        };
        String result;
        result = "" + int2strAr(Integer.parseInt(d.format(date)));
        result += " " + mois[Integer.parseInt(m.format(date)) - 1];
        result += " " + int2strAr(Integer.parseInt(y.format(date)));
        return result;
    }

    public static String dateToStrH(Date date) {
        if(date == null) {
            return null;
        }
        SimpleDateFormat d = new SimpleDateFormat("dd");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        Calendar today = Calendar.getInstance();
        today.set(Integer.parseInt(y.format(date)), Integer.parseInt(m.format(date)) - 1, Integer.parseInt(d.format(date)));

        return writeIslamicDate(today);
    }

    public static String dateToStrArH(Date date) {
        if(date == null) {
            return null;
        }
        SimpleDateFormat d = new SimpleDateFormat("dd");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        Calendar today = Calendar.getInstance();
        today.set(Integer.parseInt(y.format(date)), Integer.parseInt(m.format(date)) - 1, Integer.parseInt(d.format(date)));

        return writeIslamicDateAr(today);
    }

    //------------------------------------------------------
    static double gmod(double n, double m) {
        return ((n % m) + m) % m;
    }

    static double[] kuwaiticalendar(Calendar today, boolean adjust) {
        int adj;
        if (adjust) {
            adj = 0;
        } else {
            adj = 1;
        }

        if (adjust) {
            int adjustmili = 1000 * 60 * 60 * 24 * adj;
            long todaymili = today.getTimeInMillis() + adjustmili;
            today.setTimeInMillis(todaymili);
        }
        double day = today.get(Calendar.DAY_OF_MONTH);
        double month = today.get(Calendar.MONTH);
        double year = today.get(Calendar.YEAR);

        double m = month + 1;
        double y = year;
        if (m < 3) {
            y -= 1;
            m += 12;
        }

        double a = Math.floor(y / 100.);
        double b = 2 - a + Math.floor(a / 4.);

        if (y < 1583) {
            b = 0;
        }
        if (y == 1582) {
            if (m > 10) {
                b = -10;
            }
            if (m == 10) {
                b = 0;
                if (day > 4) {
                    b = -10;
                }
            }
        }

        double jd = Math.floor(365.25 * (y + 4716)) + Math.floor(30.6001 * (m + 1)) + day
                + b - 1524;

        b = 0;
        if (jd > 2299160) {
            a = Math.floor((jd - 1867216.25) / 36524.25);
            b = 1 + a - Math.floor(a / 4.);
        }
        double bb = jd + b + 1524;
        double cc = Math.floor((bb - 122.1) / 365.25);
        double dd = Math.floor(365.25 * cc);
        double ee = Math.floor((bb - dd) / 30.6001);
        day = (bb - dd) - Math.floor(30.6001 * ee);
        month = ee - 1;
        if (ee > 13) {
            cc += 1;
            month = ee - 13;
        }
        year = cc - 4716;

        double wd = gmod(jd + 1, 7) + 1;

        double iyear = 10631. / 30.;
        double epochastro = 1948084;
        double epochcivil = 1948085;

        double shift1 = 8.01 / 60.;

        double z = jd - epochastro;
        double cyc = Math.floor(z / 10631.);
        z = z - 10631 * cyc;
        double j = Math.floor((z - shift1) / iyear);
        double iy = 30 * cyc + j;
        z = z - Math.floor(j * iyear + shift1);
        double im = Math.floor((z + 28.5001) / 29.5);
        if (im == 13) {
            im = 12;
        }
        double id = z - Math.floor(29.5001 * im - 29);

        double[] myRes = new double[8];

        myRes[0] = day; // calculated day (CE)  
        myRes[1] = month - 1; // calculated month (CE)  
        myRes[2] = year; // calculated year (CE)  
        myRes[3] = jd - 1; // julian day number  
        myRes[4] = wd - 1; // weekday number  
        myRes[5] = id; // islamic date  
        myRes[6] = im - 1; // islamic month  
        myRes[7] = iy; // islamic year  

        return myRes;
    }

    static String writeIslamicDate(Calendar today) {
        /*String[] wdNames = {"Ahad", "Ithnin", "Thulatha", "Arbaa", "Khams",
         "Jumuah", "Sabt"};*/
        String[] iMonthNames = {"Muharram", "Safar", "Rabi'ul Awwal",
            "Rabi'ul Akhir", "Jumadal Ula", "Jumadal Akhira", "Rajab",
            "Sha'ban", "Ramadan", "Shawwal", "Dhul Qa'ada", "Dhul Hijja"};
        // This Value is used to give the correct day +- 1 day  
        boolean dayTest = true;
        double[] iDate = kuwaiticalendar(today, dayTest);
        String outputIslamicDate = int2str((int) iDate[5]) + " " + iMonthNames[(int) iDate[6]] + " " + int2str((int) iDate[7]);

        return outputIslamicDate;
    }

    public static Date dateTimeGrToH(Date date) {
        if(date == null) {
            return null;
        }
        SimpleDateFormat d = new SimpleDateFormat("dd");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        SimpleDateFormat h = new SimpleDateFormat("kk");
        SimpleDateFormat i = new SimpleDateFormat("mm");

        Calendar today = Calendar.getInstance();
        today.set(Integer.parseInt(y.format(date)), Integer.parseInt(m.format(date)) - 1, Integer.parseInt(d.format(date)), Integer.parseInt(h.format(date)), Integer.parseInt(i.format(date)));

        boolean dayTest = true;
        double[] iDate = kuwaiticalendar(today, dayTest);
        today.set((int) iDate[7], (int) iDate[6], (int) iDate[5], Integer.parseInt(h.format(date)), Integer.parseInt(i.format(date)));

        return today.getTime();
    }

    public static Date dateGrToH(Date date) {
        if(date == null) {
            return null;
        }
        SimpleDateFormat d = new SimpleDateFormat("dd");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat y = new SimpleDateFormat("yyyy");

        Calendar today = Calendar.getInstance();
        today.set(Integer.parseInt(y.format(date)), Integer.parseInt(m.format(date)) - 1, Integer.parseInt(d.format(date)));

        boolean dayTest = true;
        double[] iDate = kuwaiticalendar(today, dayTest);
        today.set((int) iDate[7], (int) iDate[6], (int) iDate[5]);

        return today.getTime();
    }

    //Integer to String Fr
    static String int2str(Integer a) {
        if (a < 0) {
            return "moins " + int2str(-a);
        }
        if (a < 17) {
            switch (a) {
                case 0:
                    return "";
                case 1:
                    return "un";
                case 2:
                    return "deux";
                case 3:
                    return "trois";
                case 4:
                    return "quatre";
                case 5:
                    return "cinq";
                case 6:
                    return "six";
                case 7:
                    return "sept";
                case 8:
                    return "huit";
                case 9:
                    return "neuf";
                case 10:
                    return "dix";
                case 11:
                    return "onze";
                case 12:
                    return "douze";
                case 13:
                    return "treize";
                case 14:
                    return "quatorze";
                case 15:
                    return "quinze";
                case 16:
                    return "seize";
            }
        } else if (a < 20) {
            return "dix-" + int2str(a - 10);
        } else if (a < 100) {
            if (a % 10 == 0) {
                switch (a) {
                    case 20:
                        return "vingt";
                    case 30:
                        return "trente";
                    case 40:
                        return "quarante";
                    case 50:
                        return "cinquante";
                    case 60:
                        return "soixante";
                    case 70:
                        return "soixante-dix";
                    case 80:
                        return "quatre-vingt";
                    case 90:
                        return "quatre-vingt-dix";
                }
            } else if (a < 70) {
                return int2str(a - a % 10) + " " + int2str(a % 10);
            } else if (a < 80) {
                return int2str(60) + " " + int2str(a % 20);
            } else {
                return int2str(80) + " " + int2str(a % 20);
            }
        } else if (a == 100) {
            return "cent";
        } else if (a < 200) {
            return int2str(100) + " " + int2str(a % 100);
        } else if (a < 1000) {
            return int2str((int) (a / 100)) + " " + int2str(100) + " " + int2str(a % 100);
        } else if (a == 1000) {
            return "mille";
        } else if (a < 2000) {
            return int2str(1000) + " " + int2str(a % 1000) + " ";
        } else if (a < 1000000) {
            return int2str((int) (a / 1000)) + " " + int2str(1000) + " " + int2str(a % 1000);
        }

        //on pourrait pousser pour aller plus loin, mais c'est sans interret pour ce projet, et pas interessant, c'est pas non plus compliqué...
        return null;
    }

    //Integer to String Ar
    static String int2strAr(Integer a) {

        String dec[] = {
            "ثلاث",
            "أربع",
            "خمس",
            "ست",
            "سبع",
            "ثمان",
            "تسع"
        };
        if (a < 0) {
            return "moins " + int2str(-a);
        }
        if (a < 20) {
            switch (a) {
                case 0:
                    return "";
                case 1:
                    return "واحد";
                case 2:
                    return "إثنان";
                case 3:
                    return "ثلاثة";
                case 4:
                    return "أربعة";
                case 5:
                    return "خمسة";
                case 6:
                    return "ستة";
                case 7:
                    return "سبعة";
                case 8:
                    return "ثمانية";
                case 9:
                    return "تسعة";
                case 10:
                    return "عشرة";
                case 11:
                    return "إحدى عشر";
                case 12:
                    return "اثنى عشر";
                case 13:
                    return "ثلاثة عشر";
                case 14:
                    return "أربعة عشر";
                case 15:
                    return "خمسة عشر";
                case 16:
                    return "ستة عشر";
                case 17:
                    return "سبعة عشر";
                case 18:
                    return "ثمانية عشر";
                case 19:
                    return "تسعة عشر";
            }
        } else if (a < 100) {
            if (a % 10 == 0) {
                switch (a) {
                    case 20:
                        return "عشرون";
                    case 30:
                        return "ثلاثون";
                    case 40:
                        return "أربعون";
                    case 50:
                        return "خمسون";
                    case 60:
                        return "ستون";
                    case 70:
                        return "سبعون";
                    case 80:
                        return "ثمانون";
                    case 90:
                        return "تسعون";
                }
            } else {
                return int2strAr(a % 10) + " و " + int2strAr(a - a % 10);
            }
        } else if (a == 100) {
            return "مئة";
        } else if (a < 200) {
            return int2strAr(100) + " و " + int2strAr(a % 100);
        } else if (a == 200) {
            return "مئتان";
        } else if (a < 300) {
            return int2strAr(200) + " و " + int2strAr(a % 100);
        } else if (a < 1000) {
            return dec[(int) (a / 100) - 3] + int2strAr(100) + " و " + int2strAr(a % 100);
        } else if (a == 1000) {
            return "ألف";
        } else if (a < 2000) {
            return int2strAr(1000) + " و " + int2strAr(a % 1000);
        } else if (a == 2000) {
            return "ألفين";
        } else if (a < 3000) {
            return int2strAr(2000) + " و " + int2strAr(a % 1000);
        } else if (a < 1000000) {
            return int2strAr((int) (a / 1000)) + " " + int2strAr(1000) + " " + int2strAr(a % 1000);
        }
        //on pourrait pousser pour aller plus loin, mais c'est sans interret pour ce projet, et pas interessant, c'est pas non plus compliqué...
        return null;
    }

    private static String writeIslamicDateAr(Calendar today) {
        /*String[] wdNames = {"Ahad", "Ithnin", "Thulatha", "Arbaa", "Khams",
         "Jumuah", "Sabt"};*/
        String[] iMonthNames = {
            "محرم",
            "صفر",
            "ربيع الأول",
            "ربيع الثاني",
            "جمادى الأولى",
            "جمادى الثانية",
            "رجب",
            "شعبان",
            "رمضان",
            "شوال",
            "ذو القعدة",
            "ذو الحجة"
        };
        // This Value is used to give the correct day +- 1 day  
        boolean dayTest = true;
        double[] iDate = kuwaiticalendar(today, dayTest);
        String outputIslamicDate = int2strAr((int) iDate[5]) + " " + iMonthNames[(int) iDate[6]] + " " + int2strAr((int) iDate[7]);

        return outputIslamicDate;
    }

    static public String md5(String args) {
        byte[] uniqueKey = args.getBytes();
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        StringBuilder hashString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1) {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            } else {
                hashString.append(hex.substring(hex.length() - 2));
            }
        }

        return hashString.toString();
    }
}