package ru.sbrf.esipov.hw;


import java.util.ArrayList;

class Benchmark implements BenchmarkMBean {
    private final int loopCounter;
    private volatile int size = 0;

    public Benchmark( int loopCounter ) {
        this.loopCounter = loopCounter;
    }

    void run() throws InterruptedException {
        int unDeletedObjectSize = 0;
        ArrayList<String> al = new ArrayList<>();
        for ( int idx = 0; idx < loopCounter; idx++ ) {
            int local = size;
            for ( int i = unDeletedObjectSize; i < unDeletedObjectSize + local; i++ ) {
                if (al.size() <= i) {
                    al.add(new String(new char[0]));
                } else {
                    al.set(i, new String(new char[0]));
                }
            }
            unDeletedObjectSize += (local / 2);
            Thread.sleep( 380 ); //Label_1
            //System.out.println(al.size());
            //Major: 8 4630 ms.
            //Minor: 31 1033 ms.

            //Major: 24 17253 ms.
            //Minor: 6 603 ms.
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize( int size ) {
        System.out.println( "new size:" + size );
        this.size = size;
    }

}
