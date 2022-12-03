package uk.ac.ed.inf;


class GeometryUtil {

    static int INF = 10000;

    /**
     *
     * Given three collinear points p, q, r,
     * the function checks if point q lies
     * on line segment 'pr'
     * @param p a point
     * @param q a point
     * @param r a point
     * @return boolean whether point q lies on the line segment pr
     */
    static boolean onSegment(LngLat p, LngLat q, LngLat r) {
        if (q.lng <= Math.max(p.lng, r.lng) &&
                q.lng >= Math.min(p.lng, r.lng) &&
                q.lat <= Math.max(p.lat, r.lat) &&
                q.lat >= Math.min(p.lat, r.lat)) {
            return true;
        }
        return false;
    }

    /** To find orientation of ordered triplet (p, q, r).
     * The function returns following values
     * 0 --> p, q and r are collinear
     * 1 --> Clockwise
     * 2 --> Counterclockwise
     * */
    static int orientation(LngLat p, LngLat q, LngLat r) {
        double val = (q.lat - p.lat) * (r.lng - q.lng)
                - (q.lng - p.lng) * (r.lat - q.lat);

        if (val == 0) {
            return 0; // collinear
        }
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    /** Checking whether 2 line segments composed of 2 points each intersect
     *
     * @param p1 x component of point 1
     * @param q1 y component of point 1
     * @param p2 x component of point 2
     * @param q2 y component of point 2
     * @return true if the line segments p1q1 and p2q2 intersects
     */
    static boolean doIntersect(LngLat p1, LngLat q1,
                               LngLat p2, LngLat q2) {
        // Find the four orientations needed for
        // general and special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4) {
            return true;
        }

        // Special Cases
        // p1, q1 and p2 are collinear and
        // p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) {
            return true;
        }

        // p1, q1 and p2 are collinear and
        // q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) {
            return true;
        }

        // p2, q2 and p1 are collinear and
        // p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) {
            return true;
        }

        // p2, q2 and q1 are collinear and
        // q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) {
            return true;
        }

        return false;
    }

    /**
     * Checking if point lies inside a polygon of any concavity using the horizontal ray casting method
     *
     * @param polygon list of points
     * @param n number of vertices in polygon
     * @param p point to test
     * @return true if point p lies inside polygon[] with n vertices
     */
    static boolean isInside(LngLat polygon[], int n, LngLat p) {
        // There must be at least 3 vertices in polygon[]
        if (n < 3) {
            return false;
        }

        // Create a point for line segment from p to infinite
        LngLat extreme = new LngLat(INF, p.lat);

        // To count number of points in polygon
        // whose y-coordinate is equal to
        // y-coordinate of the point
        int decrease = 0;

        // Count intersections of the above line
        // with sides of polygon
        int count = 0, i = 0;
        do {
            int next = (i + 1) % n;

            if (polygon[i].lat == p.lat) decrease++;

            // Check if the line segment from 'p' to
            // 'extreme' intersects with the line
            // segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(polygon[i], polygon[next], p, extreme)) {
                // If the point 'p' is collinear with line
                // segment 'i-next', then check if it lies
                // on segment. If it lies, return true, otherwise false
                if (orientation(polygon[i], p, polygon[next]) == 0) {
                    return onSegment(polygon[i], p,
                            polygon[next]);
                }

                count++;
            }
            i = next;
        } while (i != 0);

        // Reduce the count by decrease amount
        // as these points would have been added twice
        count -= decrease;

        // Return true if count is odd, false otherwise
        return (count % 2 == 1);
    }
}
