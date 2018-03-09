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
            String sql = "INSERT into finance.DAILY(TICKER, TIME, DMA50, DMA200, CLSPREV, OPENCUR, BUYVOL, DIVPAY, YEARCEIL, YEARFLR) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, dailyData.getTicker());
                    ps.setTimestamp(2, dailyData.getTime());
                    ps.setDouble(3, dailyData.getDma50());
                    ps.setDouble(4, dailyData.getDma200());
                    ps.setDouble(5, dailyData.getClosePrevious());
                    ps.setDouble(6, dailyData.getOpenCurrent());
                    ps.setInt(7, dailyData.getBuyVolumes());
                    ps.setDouble(8, dailyData.getDividendPayout());
                    ps.setDouble(9, dailyData.getYearCeiling());
                    ps.setDouble(10, dailyData.getYearFloor());
                    return ps;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            //throw e;
        }
    }

}
