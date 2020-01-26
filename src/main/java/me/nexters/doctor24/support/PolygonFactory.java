package me.nexters.doctor24.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;

import lombok.experimental.UtilityClass;

/**
 * @author manki.kim
 */
@UtilityClass
public class PolygonFactory {

	public Polygon getByXZPoints(Point x, Point z) {
		List<Point> points = new ArrayList<>();
		points.add(x);
		points.add(new Point(z.getX(), x.getY()));
		points.add(z);
		points.add(new Point(x.getX(), z.getY()));
		points.add(x);
		return new Polygon(points);
	}
}
