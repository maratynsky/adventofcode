import util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day13 extends Day<Integer> {

    interface Packet extends Comparable<Packet> {
        @Override
        default int compareTo(Packet o) {
            if (this instanceof NumberPacket p1n && o instanceof NumberPacket p2n) {
                return Integer.compare(p1n.value, p2n.value);
            }


            ListPacket p1l = this instanceof ListPacket thisL ? thisL : new ListPacket(List.of(this));
            ListPacket p2l = o instanceof ListPacket oL ? oL : new ListPacket(List.of(o));


            int i = 0;
            for (; i < p1l.values.size() && i < p2l.values.size(); i++) {
                Packet p1 = p1l.values.get(i);
                Packet p2 = p2l.values.get(i);
                int compare = p1.compareTo(p2);
                if (compare == 0) continue;
                return compare;
            }
            return Integer.compare(p1l.values.size(), p2l.values.size());
        }
    }

    record ListPacket(List<Packet> values) implements Packet {
    }

    record NumberPacket(int value) implements Packet {
    }


    @Override
    protected Integer resolveP1(Stream<String> input) {
        String in = input.collect(Collectors.joining("\n"));

        String[] pairs = in.split("\n\n");

        int count = 0;
        for (int i = 0; i < pairs.length; i++) {
            String[] packetsStr = pairs[i].split("\n");
            Packet packet1 = parse(packetsStr[0].toCharArray());
            Packet packet2 = parse(packetsStr[1].toCharArray());

            int c = packet1.compareTo(packet2);
            if (c < 0) {
                count += i + 1;
            }
        }

        return count;

    }

    static Packet parse(char[] in) {
        return parse(in, 0).y();
    }

    static Pair<Integer, Packet> parse(char[] in, int index) {
        if (in[index] == ',') index++;
        if (in[index] == '[') {
            return parseList(in, index);
        } else {
            return parseNumber(in, index);
        }
    }

    static Pair<Integer, Packet> parseList(char[] in, int index) {

        if (in[index + 1] == ']') {
            return new Pair<>(index + 2, new ListPacket(List.of()));
        }

        List<Packet> values = new ArrayList<>();
        while (index < in.length && in[index] != ']') {
            Pair<Integer, Packet> value = parse(in, index + 1);
            index = value.x();
            values.add(value.y());
        }
        return new Pair<>(index + 1, new ListPacket(values));
    }

    static Pair<Integer, Packet> parseNumber(char[] in, int index) {
        int i = index;
        StringBuilder sb = new StringBuilder();
        while (in[i] >= '0' && in[i] <= '9') {
            sb.append(in[i++]);
        }
        return new Pair<>(i, new NumberPacket(Integer.parseInt(sb.toString())));

    }


    @Override
    protected Integer resolveP2(Stream<String> input) {
        Packet p1 = parse("[[2]]".toCharArray());
        Packet p2 = parse("[[6]]".toCharArray());

        List<Packet> packets = Stream.concat(
                        Stream.of(p1, p2),
                        input.filter(s -> !s.isBlank()).map(s -> parse(s.toCharArray()))
                ).sorted(Packet::compareTo)
                .toList();

        int i1 = 0;
        int i2 = 0;
        for (int i = 0; i < packets.size() && (i1 == 0 || i2 == 0); i++) {
            if(packets.get(i).equals(p1)) i1 = i+1;
            if(packets.get(i).equals(p2)) i2 = i+1;
        }

        return i1*i2;

    }


    public static void main(String[] args) {
        new Day13().resolve();
    }

}
