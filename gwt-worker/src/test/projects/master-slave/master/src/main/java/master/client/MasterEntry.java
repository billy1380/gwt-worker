package master.client;

import java.util.logging.Logger;

import org.gwtproject.gwt.worker.shared.Worker;
import org.gwtproject.gwt.worker.shared.WorkerError;
import org.gwtproject.gwt.worker.shared.Workers;
import org.gwtproject.gwt.worker.shared.shared.SharedWorker;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;

public class MasterEntry implements EntryPoint {

	private static final Logger sLogger = Logger.getLogger("MasterEntry");

	@Override
	public void onModuleLoad() {
		Document doc = Document.get();

		SpanElement s = doc.createSpanElement();
		s.setInnerText("Master");

		doc.getBody().appendChild(s);

		// web inspector threads
		Worker w = Workers.newWorker("./Slave/Slave.worker.js");
		w.asPort().postMessage("You are a slave :)");
		w.addErrorHandler(new WorkerError.Handler() {

			@Override
			public void onWorkerError(WorkerError error) {
				sLogger.severe(error.getMessage() + "(" + error.getFileName()
						+ ":" + error.getLineNumber() + ")");
			}
		});

		 //chrome://inspect/#workers
		 SharedWorker sw = Workers.newSharedWorker("./Slave/Slave.worker.js", "looper");
		 sw.getPort();
	}

}