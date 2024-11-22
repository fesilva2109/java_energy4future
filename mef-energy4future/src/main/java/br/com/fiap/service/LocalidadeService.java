package br.com.fiap.service;

import br.com.fiap.dao.LocalidadeDao;
import br.com.fiap.dto.LocalidadeDto;
import br.com.fiap.models.Localidade;

import java.sql.SQLException;
import java.util.List;


public class LocalidadeService {
    private LocalidadeDao LocalidadeDao;

    public LocalidadeService(LocalidadeDao LocalidadeDao) {
        this.LocalidadeDao = LocalidadeDao;
    }

    public List<LocalidadeDto> getAllLocalidades() throws SQLException {
        return LocalidadeDao.readAll();
    }

    public LocalidadeDto getLocalidadeById(int id) throws SQLException {
        Localidade localidade = LocalidadeDao.read(id);
        return localidade != null ? toDTO(localidade) : null;
    }

    public void addLocalidade(LocalidadeDto LocalidadeDto) throws SQLException {
        LocalidadeDao.create(toEntity(LocalidadeDto));
    }

    public void updateLocalidade(int id, LocalidadeDto LocalidadeDto) throws SQLException {
        Localidade localidade = toEntity(LocalidadeDto);
        localidade.setId(id);
        LocalidadeDao.update(localidade);
    }

    public void deleteLocalidade(int id) throws SQLException {
        LocalidadeDao.delete(id);
    }

    private Localidade toEntity(LocalidadeDto dto) {
        return new Localidade(dto.getId(), dto.getPopulacao(), dto.getLogradouro(), dto.getBairro(), dto.getCidade(), dto.getEstado(), dto.getCep());
    }

    private LocalidadeDto toDTO(Localidade entity) {
        LocalidadeDto dto = new LocalidadeDto();
        dto.setId(entity.getId());
        dto.setPopulacao(entity.getPopulacao());
        dto.setLogradouro(entity.getLogradouro());
        dto.setBairro(entity.getBairro());
        dto.setCidade(entity.getCidade());
        dto.setEstado(entity.getEstado());
        dto.setCep(entity.getCep());
        return dto;
    }
}
