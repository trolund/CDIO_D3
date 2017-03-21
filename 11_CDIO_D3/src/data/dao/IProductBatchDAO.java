package data.dao;

import data.dto.ProductBatchDTO;

import java.util.List;

public interface IProductBatchDAO {
    ProductBatchDTO getProductBatch(int pbId) throws DALException;
    List<ProductBatchDTO> getProductBatchList() throws DALException;
    void createProductBatch(ProductBatchDTO pb) throws DALException;
    void updateProductBatch(ProductBatchDTO pb) throws DALException;
    void deleteProductBatch(int pbId) throws DALException;
}