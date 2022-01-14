package org.springframework.samples.petclinic.feeding;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class FeedingTypeFormatter implements Formatter<FeedingType>{

	private final FeedingRepository feedingRepository;

	@Autowired
	public  FeedingTypeFormatter(FeedingRepository feedingRepository) {
		this.feedingRepository = feedingRepository;
	}

	@Override
	public String print(FeedingType feedingType, Locale locale) {
		return feedingType.getName();
	}

	@Override
	public FeedingType parse(String text, Locale locale) throws ParseException {
		FeedingType feedingType = this.feedingRepository.findFeedingTypeByName(text);
		if(feedingType!=null) {
			return feedingType;
		}
		throw new ParseException("feedingType not found: " + text, 0);
	}
	
}
