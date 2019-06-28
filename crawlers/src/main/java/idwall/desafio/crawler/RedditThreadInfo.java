package idwall.desafio.crawler;

class RedditThreadInfo {
	
	private int pontuacao;
	private String subreddit;
	private String tituloThread;
	private String linkComentarios;
	private String linkThread;
	
	int getPontuacao() {
		return pontuacao;
	}
	
	void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
	
	String getSubreddit() {
		return subreddit;
	}
	
	void setSubreddit(String subreddit) {
		this.subreddit = subreddit;
	}
	
	String getTituloThread() {
		return tituloThread;
	}
	
	void setTituloThread(String tituloThread) {
		this.tituloThread = tituloThread;
	}
	
	String getLinkComentarios() {
		return linkComentarios;
	}
	
	void setLinkComentarios(String linkComentarios) {
		this.linkComentarios = linkComentarios;
	}
	
	String getLinkThread() {
		return linkThread;
	}
	
	void setLinkThread(String linkThread) {
		this.linkThread = linkThread;
	}

}
