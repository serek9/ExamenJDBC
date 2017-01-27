package persistence;

import model.City;
import model.Package;
import model.Truck;
import model.TruckDriver;

import java.sql.*;
import java.util.ArrayList;

public class TransportJDBC {

    private Connection con;

    //Selecciona todos los conductores
    public ArrayList<TruckDriver> selectTruckDrivers(ResultSet rs) throws SQLException {
        ArrayList<TruckDriver> drivers = new ArrayList<>();
        while(rs.next()){
            TruckDriver t = new TruckDriver();
            t.setDni(rs.getString("dni"));
            t.setName(rs.getString("name"));
            t.setPhone(rs.getString("phone"));
            t.setSalary(rs.getDouble("salary"));
            drivers.add(t);
        }
        rs.close();
        return drivers;
    }
    //Selecciona todos los packages
    public ArrayList<Package> selectPackages(ResultSet rs) throws SQLException {
        ArrayList<Package> packages = new ArrayList<>();
        while(rs.next()){
            Package p = new Package();
            p.setIdpackage(rs.getInt("idpackage"));
            p.setCustomer(rs.getString("customer"));
            p.setDriver(new TruckDriver(rs.getString("driver")));
            p.setCity(new City(rs.getString("city")));
            packages.add(p);
        }
        rs.close();
        return packages;
    }
    //Selecciona todos los camiones
    public ArrayList<Truck> selectTrucks(ResultSet rs) throws SQLException{
        ArrayList<Truck> trucks = new ArrayList<>();
        while(rs.next()){
            Truck t = new Truck();
            t.setIdtruck(rs.getString("idtruck"));
            t.setModel(rs.getString("model"));
            t.setCapacity(rs.getInt("capacity"));
            t.setDriver(new TruckDriver(rs.getString("driver")));
            trucks.add(t);
        }
        rs.close();
        return trucks;
    }
    //Inserta conductor
    public void insertTruckDriver(TruckDriver t) throws SQLException{
        String insert = "insert into truckdriver values (?,?,?,?);";
        PreparedStatement ps = con.prepareStatement(insert);
        ps.setString(1, t.getDni());
        ps.setString(2, t.getName());
        ps.setString(3, t.getPhone());
        ps.setDouble(4, t.getSalary());
        ps.executeUpdate();
        ps.close();
    }
    //Inserta camion
    public void insertTruck(Truck t) throws SQLException{
        String insert = "insert into truck values (?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(insert);
        ps.setString(1, t.getIdtruck());
        ps.setString(2, t.getModel());
        ps.setInt(3, t.getCapacity());
        ps.setString(4, t.getDriver().getDni());
        ps.executeUpdate();
        ps.close();
    }
    //Inserta package
    public void insertPackage(Package p) throws SQLException{
        String insert = "insert into package values (?,?,?,?);";
        PreparedStatement ps = con.prepareStatement(insert);
        ps.setInt(1, p.getIdpackage());
        ps.setString(2, p.getCustomer());
        ps.setString(3, p.getDriver().getDni());
        ps.setString(4, p.getCity().getPostalcode());
        ps.executeUpdate();
        ps.close();
    }
    //Inserta city
    public void insertCity(City c) throws SQLException{
        String insert = "insert into city values (?,?);";
        PreparedStatement ps = con.prepareStatement(insert);
        ps.setString(1, c.getPostalcode());
        ps.setString(2, c.getName());
        ps.executeUpdate();
        ps.close();
    }

    public void modificarSalarioTruckDriver(TruckDriver t) throws SQLException {
        String update = "update truckdriver set salary=? where name=?";
        PreparedStatement ps = con.prepareStatement(update);
        ps.setDouble(1, t.getSalary());
        ps.setString(2, t.getName());
        ps.executeUpdate();
        ps.close();
    }

    public ArrayList<Truck> obtenerTrucksDeDriver(String dni) throws SQLException{
        ArrayList<Truck> trucks = new ArrayList<>();
        String select = "select * from truck where driver=?";
        PreparedStatement ps = con.prepareStatement(select);
        ps.setString(1, dni);
        ResultSet rs = ps.executeQuery();
        trucks = selectTrucks(rs);
        rs.close();
        ps.close();
        return trucks;
    }

    public ArrayList<Package> obtenerPackages(String postalcode) throws SQLException{
        ArrayList<Package> packages = new ArrayList<>();
        String select = "select * from package where city=?";
        PreparedStatement ps = con.prepareStatement(select);
        ps.setString(1, postalcode);
        ResultSet rs = ps.executeQuery();
        packages = selectPackages(rs);
        rs.close();
        ps.close();
        return packages;
    }
    //conecta bdd
    public void conectar() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/transport";
        String usr = "root";
        String pass = "";
        con = DriverManager.getConnection(url, usr, pass);
    }
    //desconecta bdd
    public void desconectar() throws SQLException{
        if (con !=null){
            con.close();
        }
    }

}
