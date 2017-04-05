package data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.Connector;
import data.dto.ProductBatchComponentDTO;

public class SQLProductBatchComponentDAO implements IProductBatchComponentDAO {

	private final Connector connector;

	public SQLProductBatchComponentDAO(Connector connector) {
		this.connector = connector;
	}

	@Override
	public ProductBatchComponentDTO getProductBatchComponent(int pbId, int rbId) throws DALException {
		String getPBCSql = connector.getSQL("getPBCSql");
		PreparedStatement getPBCStmt = null;
		ResultSet rs = null;
		try {
			getPBCStmt = connector.getConnection().prepareStatement(getPBCSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			getPBCStmt.setInt(1, pbId);
			getPBCStmt.setInt(2, rbId);
			rs = getPBCStmt.executeQuery();

			if (!rs.first()) throw new DALException("ProductBatchComponent with pbId [" + pbId + "] and rbId [" + rbId + "] does not exist!");

			return new ProductBatchComponentDTO(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id"));
		} catch (SQLException e) {
			throw new DALException(e.getMessage(), e);
		} finally {
			try {
				connector.cleanup(getPBCStmt, rs);
			} catch (SQLException e) {
				throw new DALException(e.getMessage(), e);
			}
		}
	}

	@Override
	public List<ProductBatchComponentDTO> getProductBatchComponentList(int pbId) throws DALException {
		String getPBCListSql = connector.getSQL("getPBCListIdSql");
		List<ProductBatchComponentDTO> pbcList = new ArrayList<>();
		PreparedStatement getPBCListStmt = null;
		ResultSet rs = null;
		try {
			getPBCListStmt = connector.getConnection().prepareStatement(getPBCListSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			getPBCListStmt.setInt(1, pbId);
			rs = getPBCListStmt.executeQuery();
			while (rs.next()) {
				pbcList.add(new ProductBatchComponentDTO(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id")));
			}
			return pbcList;
		} catch (SQLException e) {
			throw new DALException(e.getMessage(), e);
		} finally {
			try {
				connector.cleanup(getPBCListStmt, rs);
			} catch (SQLException e) {
				throw new DALException(e.getMessage(), e);
			}
		}
	}

	@Override
	public List<ProductBatchComponentDTO> getProductBatchComponentList() throws DALException {
		String getPBCListSql = connector.getSQL("getPBCListSql");
		List<ProductBatchComponentDTO> pbcList = new ArrayList<>();
		PreparedStatement getPBCListStmt = null;
		ResultSet rs = null;
		try {
			getPBCListStmt = connector.getConnection().prepareStatement(getPBCListSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = getPBCListStmt.executeQuery();
			while (rs.next()) {
				pbcList.add(new ProductBatchComponentDTO(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id")));
			}
			return pbcList;
		} catch (SQLException e) {
			throw new DALException(e.getMessage(), e);
		} finally {
			try {
				connector.cleanup(getPBCListStmt, rs);
			} catch (SQLException e) {
				throw new DALException(e.getMessage(), e);
			}
		}
	}

	@Override
	public void createProductBatchComponent(ProductBatchComponentDTO pbc) throws DALException {
		String createPBCSql = connector.getSQL("createPBCSql");
		PreparedStatement createPBCStmt = null;
		try {
			createPBCStmt = connector.getConnection().prepareStatement(createPBCSql);
			createPBCStmt.setInt(1, pbc.getpbId());
			createPBCStmt.setInt(2, pbc.getrbId());
			createPBCStmt.setDouble(3, pbc.getTara());
			createPBCStmt.setDouble(4, pbc.getNetto());
			createPBCStmt.setInt(5, pbc.getOprId());
			createPBCStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(e.getMessage(), e);
		} finally {
			try {
				connector.cleanup(createPBCStmt);
			} catch (SQLException e) {
				throw new DALException(e.getMessage(), e);
			}
		}
	}

	@Override
	public void updateProductBatchComponent(ProductBatchComponentDTO pbc) throws DALException {
		String updatePBCSql = connector.getSQL("updatePBCSql");
		PreparedStatement updatePBCStmt = null;
		try {
			updatePBCStmt = connector.getConnection().prepareStatement(updatePBCSql);
			updatePBCStmt.setDouble(1, pbc.getTara());
			updatePBCStmt.setDouble(2, pbc.getNetto());
			updatePBCStmt.setInt(3, pbc.getpbId());
			updatePBCStmt.setInt(4, pbc.getrbId());
			updatePBCStmt.setInt(5, pbc.getOprId());
			updatePBCStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(e.getMessage(), e);
		} finally {
			try {
				connector.cleanup(updatePBCStmt);
			} catch (SQLException e) {
				throw new DALException(e.getMessage(), e);
			}
		}
	}

	@Override
	public void deleteProductBatchComponent(int pbId, int rbId) throws DALException {
		String deletePBCSql = connector.getSQL("deletePBCSql");
		PreparedStatement deletePBCStmt = null;
		try {
			deletePBCStmt = connector.getConnection().prepareStatement(deletePBCSql);
			deletePBCStmt.setInt(1, pbId);
			deletePBCStmt.setInt(2, rbId);
			deletePBCStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(e.getMessage(), e);
		} finally {
			try {
				connector.cleanup(deletePBCStmt);
			} catch (SQLException e) {
				throw new DALException(e.getMessage(), e);
			}
		}
	}

}