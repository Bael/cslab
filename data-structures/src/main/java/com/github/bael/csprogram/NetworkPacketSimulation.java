package com.github.bael.csprogram;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class NetworkPacketSimulation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int bufferSize = sc.nextInt();
        int packetsCount = sc.nextInt();
        PacketSimulation packetSimulation = new PacketSimulation(bufferSize);

        for (int i = 0; i < packetsCount; i++) {
            int startTime = packetSimulation.addPacket(sc.nextInt(), sc.nextInt());
            System.out.println(startTime);
        }

//        System.out.println("test1 ");
//        test1();
//
//        System.out.println("test2 ");
//        test2();
    }

    private static void test1() {
        PacketSimulation packetSimulation = new PacketSimulation(1);
        System.out.println(packetSimulation.addPacket(0, 1));
        System.out.println(packetSimulation.addPacket(0, 1));

    }

    /**
     * 1 2
     * 0 1
     * 1 1
     */
    private static void test2() {
        PacketSimulation packetSimulation = new PacketSimulation(2);
        System.out.println(packetSimulation.addPacket(0, 1));
        System.out.println(packetSimulation.addPacket(1, 1));

    }

    private static class PacketSimulation {
        private Deque<Packet> packets = new ArrayDeque<>();
        private int bufferSize;

        public PacketSimulation(int bufferSize) {
            this.bufferSize = bufferSize;
        }

        /**
         * Время окончания обработки первого пакета в очереди
         *
         * @return время, когда первый пакет будет обработан
         * В случае если очередь пуста, -1
         */
        private int getFirstElementFinishTime() {
            if (packets.isEmpty()) {
                return -1;
            } else {
                return packets.getFirst().getProcessingTime();
            }
        }

        private int getLastElementFinishTime() {
            if (packets.isEmpty()) {
                return -1;
            } else {
                return packets.getLast().getProcessingTime();
            }
        }

        /**
         * Очищаем пакеты что уже будут обработаны к моменту времени arrival
         */
        private void clearQueueUntilArrival(int arrival) {
            while (arrival >= getFirstElementFinishTime() && !packets.isEmpty()) {
                packets.removeFirst();
            }
        }

        public int addPacket(int arrival, int duration) {
            clearQueueUntilArrival(arrival);
            if (!bufferIsFull()) {
                return processPacket(arrival, duration);
            } else {
                return -1;
            }
        }

        private boolean bufferIsFull() {
            return packets.size() >= bufferSize;
        }

        private int processPacket(int arrival, int duration) {
            int startTime = Math.max(arrival, getLastElementFinishTime());
            // сдвигаем обработку пакета вперед,
            // сможем начать только когда текущий
            // последний элемент в очереди будет обработан.
            packets.add(new Packet(startTime, duration));
            return startTime;
        }
    }

    private static class Packet {
        private int arrival;
        private int duration;

        public Packet(int arrival, int duration) {
            this.arrival = arrival;
            this.duration = duration;
        }

        @Override
        public String toString() {
            return "Packet{" +
                    "arrival=" + arrival +
                    ", duration=" + duration +
                    ", processingTime=" + getProcessingTime() + '}';
        }

        public int getProcessingTime() {
            return arrival + duration;
        }
    }
}
