package ru.sbrf.esipov.hw;

import com.sun.management.GarbageCollectionNotificationInfo;
import sun.misc.Unsafe;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/*
О формате логов
http://openjdk.java.net/jeps/158


-Xms512m
-Xmx512m
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
-XX:+UseG1GC
-XX:+UseSerialGC
*/

/*
1)
    default, time: 83 sec (82 without Label_1)
2)
    -XX:MaxGCPauseMillis=100000, time: 82 sec //Sets a target for the maximum GC pause time.
3)
    -XX:MaxGCPauseMillis=10, time: 91 sec

4)
-Xms2048m
-Xmx2048m
    time: 81 sec

5)
-Xms5120m
-Xmx5120m
    time: 80 sec

5)
-Xms20480m
-Xmx20480m
    time: 81 sec (72 without Label_1)

*/

public class GCTest {
    public static void main( String... args ) throws Exception {
        System.out.println( "Starting pid: " + ManagementFactory.getRuntimeMXBean().getName() );
        switchOnMonitoring();
        long beginTime = System.currentTimeMillis();

        DateFormat dateFormat = new SimpleDateFormat("MM.dd.YYYY HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));

        int size = 5 * 100 * 100;
        int loopCounter = 1000;
        //int loopCounter = 100000;
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName( "ru.otus:type=Benchmark" );

        Benchmark mbean = new Benchmark( loopCounter );
        mbs.registerMBean( mbean, name );
        mbean.setSize( size );
        mbean.run();

        System.out.println( "time:" + ( System.currentTimeMillis() - beginTime ) / 1000 );
    }

    private static void switchOnMonitoring() {
        AtomicInteger MinorCount = new AtomicInteger();
        AtomicInteger MajorCount = new AtomicInteger();
        AtomicLong MinorTime = new AtomicLong();
        AtomicLong MajorTime = new AtomicLong();

        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for ( GarbageCollectorMXBean gcbean : gcbeans ) {
            System.out.println( "GC name:" + gcbean.getName() );
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = ( notification, handback ) -> {
                if ( notification.getType().equals( GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION ) ) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from( (CompositeData) notification.getUserData() );
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    if (gcAction.equals("end of major GC")) {
                        MajorCount.getAndIncrement();
                        MajorTime.addAndGet(duration);
                    }
                    else {
                        MinorCount.getAndIncrement();
                        MinorTime.addAndGet(duration);
                    }


                    System.out.println( "start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)" );
                    System.out.println( "Major: " + MajorCount + " " + MajorTime + " ms.");
                    System.out.println( "Minor: " + MinorCount + " " + MinorTime + " ms.");

                    DateFormat dateFormat = new SimpleDateFormat("MM.dd.YYYY HH:mm:ss");
                    Date date = new Date();
                    System.out.println(dateFormat.format(date));
                }
            };
            emitter.addNotificationListener( listener, null, null );
        }
    }

}
