/**
 * 
 */
package com.taotaosou.lu.thread.future.fork_join;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @author tracy.lu 2017年9月18日
 */
public class FileCountingTask extends RecursiveTask<Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Path dir;

	public FileCountingTask(Path dir) {
		this.dir = dir;
	}

	@Override
	protected Integer compute() {
		int count = 0;
		List<FileCountingTask> subTasks = new ArrayList<>();
		try {
			DirectoryStream<Path> ds = Files.newDirectoryStream(dir);
			for (Path subPath : ds) {
				if (Files.isDirectory(subPath)) {
					subTasks.add(new FileCountingTask(subPath));// 对每个子目录都新建一个任务
				} else {
					count++;
				}
			}
			if (!subTasks.isEmpty()) {
				Collection<FileCountingTask> subTaskList = invokeAll(subTasks);
				for (FileCountingTask fileCountingTask : subTaskList) {
					count += fileCountingTask.join();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return count;

	}

}
