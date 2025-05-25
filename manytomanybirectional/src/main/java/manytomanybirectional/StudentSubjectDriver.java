package manytomanybirectional;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class StudentSubjectDriver {
	public static void main(String[] args) {

//		addData();
//		addStudent();
//		addSubject();
//		allocateStudent(102,2);
//		deallocateStudent(2, 102);
//		allocateSubject(11,110);
//		deallocateSubject(106, 8);
//		deleteByStudentId(4);
//		deleteBySubjectId(103);
//		findBySubjectId(102);
//		findByStudentId(2);
	}
	
	public static void addData() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Subject sub1 = new Subject();
		sub1.setName("Physics");
		sub1.setNumofdays(200);
		
		Subject sub2 = new Subject();
		sub2.setName("Chemistry");
		sub2.setNumofdays(220);
		
		Subject sub3 = new Subject();
		sub3.setName("Maths");
		sub3.setNumofdays(230);
		
		Subject sub4 = new Subject();
		sub4.setName("Bio");
		sub4.setNumofdays(180);
		
		ArrayList al =new ArrayList();
		al.add(sub1);
		al.add(sub2);
		al.add(sub3);
		al.add(sub4);
		
		
		Student s1 = new Student();
		s1.setName("Sanika");
		s1.setAge(22);
		s1.setSubject(al);
	
		
		et.begin();
		em.persist(s1);
		em.persist(sub1);
		em.persist(sub2);
		em.persist(sub3);
		em.persist(sub4);
		et.commit();
	}
	
	public static void addStudent() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Student student = new Student();
	    student.setName("vedashri");
	    student.setAge(23);
//	    student.setSubject(subject);  // Set the subjects list

	    et.begin();
	    em.persist(student);
	    et.commit();
	}

	public static void addSubject() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Subject s = new Subject();
		s.setName("Maths2");
		s.setNumofdays(60);
		
		et.begin();
		em.persist(s);
		et.commit();

	}

	public static void find() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
		Query q = em.createQuery("Select s from Subject s");
		List<Subject> li = q.getResultList();
		Student s = new Student();
		s.setName("Allen");
		s.setAge(20);
		s.setSubject(li);
		em.persist(s);
		et.commit();
	}

	public static void allocateStudent(int sub_id, int st_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Subject subject = em.find(Subject.class, sub_id);
		Student student = em.find(Student.class, st_id);

		List<Subject> li = student.getSubject();
		li.add(subject);

		et.begin();
		em.merge(student);
		et.commit();

	}

	public static void deallocateStudent(int st_id, int sub_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Subject subject = em.find(Subject.class, sub_id);
		Student student = em.find(Student.class, st_id);

		List<Subject> li = student.getSubject();
		li.remove(subject); 
		student.setSubject(li);

		et.begin();
		em.merge(student);
		et.commit();

	}

	public static void allocateSubject(int st_id, int sub_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Student student = em.find(Student.class, st_id);
		Subject subject = em.find(Subject.class, sub_id);

		et.begin();
		List<Subject> list = student.getSubject();

		list.add(subject);
		student.setSubject(list);

		em.merge(student);
		et.commit();

	}

	public static void deallocateSubject(int sub_id,int st_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Student student = em.find(Student.class, st_id);
	    Subject subject = em.find(Subject.class, sub_id);

	    List<Subject> li = student.getSubject();
	    li.remove(subject); // remove subject from student's list
	    student.setSubject(li);

	    et.begin();
	    em.merge(student);
	    et.commit();
	}


	public static void deleteByStudentId(int st_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
	    Student st = em.find(Student.class, st_id);
	    em.remove(st);
	    et.commit();
	}

	public static void deleteBySubjectId(int sub_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
		Subject sub = em.find(Subject.class, sub_id);
		em.remove(sub);
		et.commit();
	}

	public static void findBySubjectId(int sub_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Subject subject = em.find(Subject.class, sub_id);
	    System.out.println("Subject: " + subject.getName());

	    List<Student> students = em.createQuery(
	        "SELECT s FROM Student s JOIN s.subject sub WHERE sub.id = :id", Student.class)
	        .setParameter("id", sub_id)
	        .getResultList();

	    for (Student s : students) {
	        System.out.println("Student: " + s.getName() + ", Age: " + s.getAge());
	    }

	}

	public static void findByStudentId(int st_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Student student = em.find(Student.class, st_id);
	    System.out.println("Student: " + student.getName());

	    for (Subject s : student.getSubject()) {
	        System.out.println("Subject: " + s.getName() + ", Days: " + s.getNumofdays());
	    }


	}
	

}
