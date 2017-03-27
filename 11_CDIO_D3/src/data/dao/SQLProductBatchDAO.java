package data.dao;

import data.Connector;
import data.dto.ProductBatchDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLProductBatchDAO implements IProductBatchDAO {

    private final Connector connector;

    public SQLProductBatchDAO(Connector connector) {
        this.connector = connector;
    }

    @Override
    public ProductBatchDTO getProductBatch(int pbId) throws DALException {
        String getPBSql = "SELECT * FROM produktbatch WHERE pb_id = ?";
        PreparedStatement getPBStmt = null;
        ResultSet rs = null;
        try {
            getPBStmt = connector.getConnection().prepareStatement(getPBSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            getPBStmt.setInt(1, pbId);
            rs = getPBStmt.executeQuery();

            if (!rs.first())
                throw new DALException("ProductBatch with pbId [" + pbId + "] does not exist!");

            return new ProductBatchDTO(rs.getInt("pb_id"), rs.getInt("status"), rs.getInt("recept_id"));
        } catch (SQLException e) {
            throw new DALException(e.getMessage(), e);
        } finally {
            try {
                connector.cleanup(getPBStmt, rs);
            } catch (SQLException e) {
                throw new DALException(e.getMessage(), e);
            }
        }
    }

    @Override
    public List<ProductBatchDTO> getProductBatchList() throws DALException {
        String getPBListSql = "SELECT * FROM produktbatch";
        List<ProductBatchDTO> pbList = new ArrayList<>();
        PreparedStatement getPBListStmt = null;
        ResultSet rs = null;
        try {
            getPBListStmt = connector.getConnection().prepareStatement(getPBListSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = getPBListStmt.executeQuery();
            while (rs.next()) {
                pbList.add(new ProductBatchDTO(rs.getInt("pb_id"), rs.getInt("status"), rs.getInt("recept_id")));
            }
            return pbList;
        } catch (SQLException e) {
            throw new DALException(e.getMessage(), e);
        } finally {
            try {
                connector.cleanup(getPBListStmt, rs);
            } catch (SQLException e) {
                throw new DALException(e.getMessage(), e);
            }
        }
    }

    @Override
    public void createProductBatch(ProductBatchDTO pb) throws DALException {
        String createPBSql = "INSERT INTO produktbatch(pb_id, status, recept_id) VALUES (?, ?, ?)";
        PreparedStatement createPBStmt = null;
        try {
            createPBStmt = connector.getConnection().prepareStatement(createPBSql);
            createPBStmt.setInt(1, pb.getpBId());
            createPBStmt.setInt(2, pb.getStatus());
            createPBStmt.setInt(3, pb.getReceptId());
            createPBStmt.executeQuery();
        } catch (SQLException e) {
            throw new DALException(e.getMessage(), e);
        } finally {
            try {
                connector.cleanup(createPBStmt);
            } catch (SQLException e) {
                throw new DALException(e.getMessage(), e);
            }
        }
    }

    @Override
    public void updateProductBatch(ProductBatchDTO pb) throws DALException {
        String updatePBSql = "UPDATE produktbatch SET status = ? WHERE pb_id = ? AND recept_id = ?";
        PreparedStatement updatePBStmt = null;
        try {
            updatePBStmt = connector.getConnection().prepareStatement(updatePBSql);
            updatePBStmt.setInt(1, pb.getStatus());
            updatePBStmt.setInt(2, pb.getpBId());
            updatePBStmt.setInt(3, pb.getReceptId());
            updatePBStmt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException(e.getMessage(), e);
        } finally {
            try {
                connector.cleanup(updatePBStmt);
            } catch (SQLException e) {
                throw new DALException(e.getMessage(), e);
            }
        }
    }

    @Override
    public void deleteProductBatch(int pbId) throws DALException {
        String deletePBSql = "DELETE FROM produktbatch WHERE pb_id = ?";
        PreparedStatement deletePBStmt = null;
        try {
            deletePBStmt = connector.getConnection().prepareStatement(deletePBSql);
            deletePBStmt.setInt(1, pbId);
            deletePBStmt.executeQuery();
        } catch (SQLException e) {
            throw new DALException(e.getMessage(), e);
        } finally {
            try {
                connector.cleanup(deletePBStmt);
            } catch (SQLException e) {
                throw new DALException(e.getMessage(), e);
            }
        }
    }

}