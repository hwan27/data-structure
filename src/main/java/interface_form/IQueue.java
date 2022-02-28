package interface_form;

public interface IQueue <E>{

    boolean offer(E e);

    E poll();

    E peek();
}
