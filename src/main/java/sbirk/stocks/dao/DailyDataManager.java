package sbirk.stocks.dao;

import java.sql.Timestamp;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import sbirk.stocks.domain.DailyData;

@Component
public class DailyDataManager {

	private static SessionFactory factory;
	
	public DailyDataManager () {
		DailyData daily = new DailyData();
		daily.setTicker("test");
		daily.setTime(new Timestamp(System.currentTimeMillis()));
		daily.setBuyVolumes(new Long(1234));
		addDailyData(daily);
		
	}
	public void addDailyData (DailyData dailyData) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(dailyData);
			tx.commit();
			System.out.println("Data entered successfully");
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
