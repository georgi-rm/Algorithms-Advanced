import java.util.*;

public class Main {
    public static class Point {
        private int coordinate;
        private boolean isEnd;

        public Point(int coordinate, boolean isEnd) {
            this.coordinate = coordinate;
            this.isEnd = isEnd;
        }

        public int getCoordinate() {
            return coordinate;
        }

        public boolean isEnd() {
            return isEnd;
        }
    }

    private static class Rectangle {
        private Point minX;
        private Point maxX;
        private Point minY;
        private Point maxY;

        public Rectangle(Point minX, Point maxX, Point minY, Point maxY) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }

        public Point getMinX() {
            return minX;
        }

        public Point getMaxX() {
            return maxX;
        }

        public Point getMinY() {
            return minY;
        }

        public Point getMaxY() {
            return maxY;
        }
    }

    public static class Section {
        private int start;
        private int end;
        private List<Point> heightPoints;

        public Section(int start, int end) {
            this.start = start;
            this.end = end;
            this.heightPoints = new ArrayList<>();
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public List<Point> getHeightPoints() {
            return heightPoints;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rectangleCount = Integer.parseInt(scanner.nextLine());

        List<Rectangle> rectangles = new ArrayList<>();
        List<Point> horizontalCoordinatePoints = new ArrayList<>();

        for (int i = 0; i < rectangleCount; i++) {
            int[] coordinates = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            Point minX = new Point(coordinates[0], false);
            Point maxX = new Point(coordinates[1], true);
            Point minY = new Point(coordinates[2], false);
            Point maxY = new Point(coordinates[3], true);

            rectangles.add(new Rectangle(minX, maxX, minY, maxY));
            horizontalCoordinatePoints.add(minX);
            horizontalCoordinatePoints.add(maxX);
        }

        rectangles.sort(Comparator.comparingInt(f -> f.getMinX().getCoordinate()));
        horizontalCoordinatePoints.sort(Comparator.comparing(Point::getCoordinate));

        List<Section> sections = new LinkedList<>();

        for (int i = 1; i < horizontalCoordinatePoints.size(); i++) {
            Point start = horizontalCoordinatePoints.get(i - 1);
            Point end = horizontalCoordinatePoints.get(i);

            Section section = new Section(start.getCoordinate(), end.getCoordinate());

            sections.add(section);
        }

        for (Rectangle rectangle : rectangles) {
            int start = rectangle.getMinX().getCoordinate();
            int end = rectangle.getMaxX().getCoordinate();
            for (Section section : sections) {
                if (section.getStart() == start
                        || section.getEnd() == end
                        || (section.getEnd() < end && section.getStart() > start)) {
                    section.getHeightPoints().add(rectangle.getMinY());
                    section.getHeightPoints().add(rectangle.getMaxY());
                }
            }
        }

        int area = 0;
        for (Section section : sections) {
            if (section.getHeightPoints().size() < 4) {
                continue;
            }

            int width = section.getEnd() - section.getStart();
            int height = calculateLineCoverLength(section.getHeightPoints(), 2);

            area += width * height;
        }

        System.out.println(area);
    }

    public static int calculateLineCoverLength(List<Point> points, int coverRequired) {
        points.sort(Comparator.comparing(Point::getCoordinate));

        int lineCoverCount = 0;
        int start = points.get(0).getCoordinate();
        int coverLength = 0;

        for (Point point : points) {
            if (lineCoverCount >= coverRequired) {
                coverLength = coverLength + (point.getCoordinate() - start);
            }

            if (!point.isEnd()) {
                lineCoverCount++;
            } else {
                lineCoverCount--;
            }

            start = point.getCoordinate();
        }

        return coverLength;
    }

}