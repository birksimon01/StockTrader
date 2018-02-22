package sbirk.stocks.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import sbirk.stocks.domain.DailyData;

@Component
public class DailyDataManager {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource (DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public DailyDataManager () {
		System.out.println("IM HERE DONT WORRY");
		System.out.println(jdbcTemplate == null);
		addTheThing();
		System.out.println("probably worry now");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addTheThing() {
		addDailyDataEntry (new DailyData(
				"IBM",
				new Timestamp(System.currentTimeMillis()),
				new Double(3.3),
				new Double(4.4),
				new Integer(55555),
				new Double(6.6),
				new Double(7.7)));
	}
	
	private String toDate(int daysFromToday) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, daysFromToday);
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date).toString();
    }
	
	public void addDailyDataEntry (DailyData dailyData)
    {
        try {
            String sql = "INSERT into FINANCE.DAILY(TICKER, TIMESTAMP, DMA50, DMA200, BUYVOL, CLSPREV, OPENCUR) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, dailyData.getTicker());
                    ps.setTimestamp(2, dailyData.getTime());
                    ps.setDouble(3, dailyData.getDma50());
                    ps.setDouble(4, dailyData.getDma200());
                    ps.setInt(5, dailyData.getBuyVolumes());
                    ps.setDouble(6, dailyData.getClosePrevious());
                    ps.setDouble(7, dailyData.getOpenCurrent());
                    return ps;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            //throw e;
        }
    }

}
