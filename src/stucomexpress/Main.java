package stucomexpress;

import java.sql.SQLException;

import model.City;
import model.Package;
import model.Truck;
import model.TruckDriver;
import persistence.TransportJDBC;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        TransportJDBC gestor = new TransportJDBC();
        try {
            System.out.println("Estableciendo conexión con la bbdd...");
            gestor.conectar();
            System.out.println("Conexión establecida.");
            City c1 = new City("08755", "Castellbisbal");
            //gestor.insertCity(c1);
            System.out.println("City c1 insertada en la bdd.");
            TruckDriver td1 = new TruckDriver("47832312V", "Sergio", "664702226", 720.20);
            //gestor.insertTruckDriver(td1);
            System.out.println("TruckDriver td1 insertado en la bdd.");
            Truck t1 = new Truck("123456B", "Volvo", 2000, td1);
            Truck t2 = new Truck("123456C", "Mercedes", 3000, td1);
            //gestor.insertTruck(t2);
            System.out.println("Truck t1 insertado en la bdd.");
            Package p1 = new Package(18, "Alberto", td1, c1);
            //gestor.insertPackage(p1);
            System.out.println("Package p1 insertado en la bdd.");
            td1.setSalary(1200.0);
            gestor.modificarSalarioTruckDriver(td1);
            System.out.println("Salario de td1 modificado.");
            System.out.println("Camiones de Sergio:");
            System.out.println(gestor.obtenerTrucksDeDriver("47832312V"));
            System.out.println("Packages enviados a Castellbisbal:");
            System.out.println(gestor.obtenerPackages("08755"));
            gestor.desconectar();
            System.out.println("Se ha desconectado de la bdd.");
        }catch (SQLException ex){
            System.out.println("Error con la BBDD: "+ex.getMessage());
        }
    }
}