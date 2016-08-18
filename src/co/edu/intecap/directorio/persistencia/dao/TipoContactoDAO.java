/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.intecap.directorio.persistencia.dao;

import co.edu.intecap.directorio.persistencia.conexion.ConexionBD;
import co.edu.intecap.directorio.persistencia.vo.TipoContacto;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author capacitaciones
 */
public class TipoContactoDAO {

    private Connection cnn;

    public TipoContactoDAO(Connection cnn) {
        this.cnn = cnn;
    }

    public void insertar(TipoContacto entidad) {
        try {
            String sql = "INSERT INTO tipo_contacto(nombre,estado) VALUES (?,?)";
            PreparedStatement sentencia = cnn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, entidad.getNombre());
            sentencia.setBoolean(2, entidad.isEstado());
            sentencia.executeUpdate();
            ResultSet resultado = sentencia.getGeneratedKeys();

            if (resultado.next()) {//si devuelve true es que hay un registro por lo menos
                entidad.setIdTipoContacto(resultado.getLong(1));
            }
        } catch (Exception e) {
            System.out.println("Error al insertar");
            e.printStackTrace(System.err);
        } finally { //lo que va aca siempre se ejecuta sin importantar el try y el catch
            ConexionBD.desconectar(cnn);
        }
    }

    public void editar(TipoContacto entidad) {
        try {
            String sql = "UPDATE tipo_contacto set nombre = ?, estado = ?";
            PreparedStatement sentencia = cnn.prepareStatement(sql);
            sentencia.setString(1, entidad.getNombre());
            sentencia.setBoolean(2, entidad.isEstado());
            sentencia.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al editar");
            e.printStackTrace(System.err);
        } finally { //lo que va aca siempre se ejecuta sin importantar el try y el catch
            ConexionBD.desconectar(cnn);
        }
    }

    public List<TipoContacto> consultar() {
        List<TipoContacto> listaContactos = new ArrayList<>();
        try {
            String sql = "Select * from tipo_contacto";
            PreparedStatement sentencia = cnn.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                TipoContacto miContacto = new TipoContacto();
                miContacto.setIdTipoContacto(resultado.getLong("id_tipo_contacto"));
                miContacto.setNombre(resultado.getString("nombre"));
                miContacto.setEstado(resultado.getBoolean("estado"));
                listaContactos.add(miContacto);
            }
        } catch (Exception e) {
            System.out.println("Error al editar");
            e.printStackTrace(System.err);
        } finally {
            ConexionBD.desconectar(cnn);
        }
        return listaContactos;
    }

    public TipoContacto consultar(long id) {
        TipoContacto miContacto = new TipoContacto();
        try {
            String sql = "Select * from tipo_contacto where id_tipo_contacto = ?";
            PreparedStatement sentencia = cnn.prepareStatement(sql);
            sentencia.setLong(1, id);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                miContacto.setIdTipoContacto(resultado.getLong("id_tipo_contacto"));
                miContacto.setNombre(resultado.getString("nombre"));
                miContacto.setEstado(resultado.getBoolean("estado"));
            }
        } catch (Exception e) {
            System.out.println("Error al editar");
            e.printStackTrace(System.err);
        } finally {
            ConexionBD.desconectar(cnn);
        }
        return miContacto;
    }
}
