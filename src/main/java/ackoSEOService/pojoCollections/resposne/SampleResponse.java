package ackoSEOService.pojoCollections.resposne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SampleResponse {

	private FormPost formPost ;

	public FormPost getFormPost() {
		return formPost;
	}

	public void setFormPost(FormPost formPost) {
		this.formPost = formPost;
	}

	
}
