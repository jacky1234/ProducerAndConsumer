package jack.blockingqueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ���ߣ����. ���䣺847564732@qq.com ��������:2016-3-7 ���ܣ�
 */

public class BlockingQueueTest {
	public static void main(String[] args) throws InterruptedException {
		// ����һ������Ϊ10�Ļ������
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10);

		Producer producer1 = new Producer(queue);
		Producer producer2 = new Producer(queue);
		Producer producer3 = new Producer(queue);
		Consumer consumer = new Consumer(queue);

		// ����Executors
		ExecutorService service = Executors.newCachedThreadPool();
		// �����߳�
		new Thread(producer1).start();
		new Thread(producer2).start();
		new Thread(producer3).start();
		new Thread(consumer).start();

		// service.submit(producer1);
		// service.submit(producer2);
		// service.submit(producer3); //��Ҫ����ֵ���� submint
		// service.execute(consumer); //����Ҫ����ֵ���� execute
		// service.invokeAll(..);

		// ִ��10s
		Thread.sleep(2 * 1000);
		producer1.stop(); // �������˳�
		producer2.stop(); // �������˳�
		producer3.stop(); // �������˳�
		System.out.println("�����������˳�");

		Thread.sleep(2000);
		// �˳�Executor
		service.shutdown(); // ��������submit execute�µ�task���Ѿ�submit�Ľ�����ִ�С�
		System.out.println("�ر��̳߳�");
	}
}
