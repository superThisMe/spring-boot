package com.springdemo.bootboard.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.springdemo.bootboard.commons.Util;
import com.springdemo.bootboard.entity.BoardEntity;
import com.springdemo.bootboard.entity.BoardFileEntity;
import com.springdemo.bootboard.service.BoardService;

@Controller
@RequestMapping(path = { "/board" })
public class BoardController {

	@Autowired
	@Qualifier("boardService")
	BoardService boardService;

	@GetMapping(path = { "/list" })
	public String showList(Model model) {

		List<BoardEntity> boards = boardService.findBoardList();
		// View에서 읽을 수 있도록 저장 (실제로는 Request객체에 저장)
		model.addAttribute("boards", boards);

		return "board/list";
	}

	@GetMapping(path = { "/write" })
	public String showWriteForm() {
		return "board/write";
	}

	@PostMapping(path = { "/write" })
//	public String doWrite(Board board, MultipartFile[] files) {
	public String doWrite(BoardEntity board, MultipartHttpServletRequest req) {

		board.setFileList(parseAndSaveUploadFiles(req));

		try {
			boardService.writeBoard(board);
			System.out.println(board.getBoardIdx()); // 자동 증가 값 확인 코드
		} catch (Exception ex) {
			System.out.println("등록 실패");
			ex.printStackTrace();
		}

		return "redirect:list";
	}

	@GetMapping(path = { "/detail/{boardIdx}" })
	public String showDetail(@PathVariable("boardIdx") int boardIdx, Model model) {

		BoardEntity board = boardService.findBoardByBoardIdx(boardIdx);
		if (board == null) {
			return "redirect:/board/list";
		}

		model.addAttribute("board", board);

		return "board/detail";
	}

	@PutMapping(path = { "/update" })
	public String updateBoard(BoardEntity board) {

		boardService.updateBoard(board);

		return "redirect:detail/" + board.getBoardIdx();
	}

//	@PostMapping(path = { "/delete" })
	@DeleteMapping(path = { "/delete/{boardIdx}" })
	public String deleteBoard(@PathVariable int boardIdx, BoardEntity board) {

		boardService.deleteBoard(board);

		return "redirect:/board/list";
	}

	@GetMapping("/download/{fileIdx}")
	public void download(@PathVariable int fileIdx, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BoardFileEntity file = boardService.findBoardFileByFileIdx(fileIdx);

		response.setContentType("application/octet-stream");

		response.addHeader("Content-Disposition", String.format("Attachment;Filename=\"%s\"",
				new String(file.getUserFileName().getBytes("UTF-8"), "ISO-8859-1")));

		ServletContext application = request.getServletContext();
		String file2 = application.getRealPath("/upload-files/" + file.getSavedFileName());

		byte[] fileContents = FileUtils.readFileToByteArray(new File(file2));

		ServletOutputStream sos = response.getOutputStream();
		sos.write(fileContents);
		sos.flush();
		sos.close();
	}

	/////////////////////////////////////////////////////////////////////////////////

	List<BoardFileEntity> parseAndSaveUploadFiles(MultipartHttpServletRequest req) {

		ArrayList<BoardFileEntity> boardFiles = new ArrayList<>();

		if (!ObjectUtils.isEmpty(req)) {

			String dirPath = req.getServletContext().getRealPath("/upload-files/");

			System.out.println(dirPath);

			Iterator<String> iter = req.getFileNames();
			while (iter.hasNext()) { // 다음 항목이 있는지 확인
				String name = iter.next(); // 다음 항목 반환
				List<MultipartFile> files = req.getFiles(name); // 파일 들의 이름을 리스트 꼴로 여러가지 담음.

				for (MultipartFile file : files) {

					String originalFileName = file.getOriginalFilename();

					if (originalFileName == null || originalFileName.length() == 0) {
						continue;
					}

					String uniqueFileName = Util.makeUniqueFileName(originalFileName);
					try {

						file.transferTo(new File(dirPath, uniqueFileName));

						BoardFileEntity boardFile = new BoardFileEntity();
						boardFile.setUserFileName(originalFileName);
						boardFile.setSavedFileName(uniqueFileName);
						boardFile.setFileSize(file.getSize());
						boardFile.setCreatedFileDatetime(new Date());
						boardFile.setCreatorId("userone");
						boardFiles.add(boardFile);

					} catch (Exception e) {
						e.printStackTrace();
					}

					// log.info("File Name : " + file.getOriginalFilename()); // 콘솔 창에 뜬다.
					// log.info("File size : " + file.getSize()); // 콘솔 창에 뜬다.
				}
			}
		}

		return boardFiles;
	}

}
