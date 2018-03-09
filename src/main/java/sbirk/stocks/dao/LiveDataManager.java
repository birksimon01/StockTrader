package sbirk.stocks.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import sbirk.stocks.domain.LiveData;

@Component
public class LiveDataManager {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource (DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void addLiveDataEntry (LiveData liveData)
    {
        try {
            String sql = "INSERT into finance.LIVE(TICKER, TIME, PRICE) VALUES (?, ?, ?)";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, liveData.getTicker());
                    ps.setTimestamp(2, liveData.getTime());
	                ps.setDouble(3, liveData.getQuotePrice());
                    return ps;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            //throw e;
        }
    }
}
