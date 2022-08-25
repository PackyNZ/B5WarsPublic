package net.b5gamer.b5wars.ajax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.awt.geom.GeneralShape;
import net.b5gamer.awt.geom.ShapeUtil;
import net.b5gamer.b5wars.io.DataManager;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.util.SVGUtil;

public class StructuralUnitBean extends UnitBean {

	private String           silhouette = null;
	private List<SystemBean> systems    = new ArrayList<SystemBean>();
	
	public StructuralUnitBean() {
	}
	
	public StructuralUnitBean(final StructuralUnit unit) {
		super(unit);
		
		try {
			GeneralShape shape = ShapeUtil.construct(ShapeUtil.parse(SVGUtil.readShape(DataManager.getSilhouetteDao().load(unit.getHull()))));
			shape.transform(SVGUtil.RESCALE_TRANSFORM);
			
			this.silhouette = ShapeUtil.toSVG(shape);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Iterator<System> iterator = unit.getSystemsOfClass(System.class, true).iterator(); iterator.hasNext();) {
			this.systems.add(AjaxBeanFactory.getSystemBean(iterator.next()));
		}
	}

	public String getSilhouette() {
		return silhouette;
	}

	public void setSilhouette(final String silhouette) {
		this.silhouette = silhouette;
	}

	public List<SystemBean> getSystems() {
		return systems;
	}

	public void setSystems(final List<SystemBean> systems) {
		this.systems = systems;
	}
	
}
