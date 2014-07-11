/* LanguageTool, a natural language style checker
 * Copyright (C) 2007 Daniel Naber (http://www.danielnaber.de)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool.language;

import java.util.Arrays;
import java.util.List;

import org.languagetool.Language;
import org.languagetool.chunking.Chunker;
import org.languagetool.chunking.EnglishChunker;
import org.languagetool.rules.*;
import org.languagetool.rules.en.AvsAnRule;
import org.languagetool.rules.en.CompoundRule;
import org.languagetool.rules.en.ContractionSpellingRule;
import org.languagetool.rules.en.EnglishUnpairedBracketsRule;
import org.languagetool.rules.en.EnglishWordRepeatBeginningRule;
import org.languagetool.rules.en.EnglishWordRepeatRule;
import org.languagetool.synthesis.Synthesizer;
import org.languagetool.synthesis.en.EnglishSynthesizer;
import org.languagetool.tagging.Tagger;
import org.languagetool.tagging.disambiguation.Disambiguator;
import org.languagetool.tagging.disambiguation.rules.XmlRuleDisambiguator;
import org.languagetool.tagging.en.EnglishTagger;
import org.languagetool.tokenizers.SRXSentenceTokenizer;
import org.languagetool.tokenizers.SentenceTokenizer;
import org.languagetool.tokenizers.WordTokenizer;
import org.languagetool.tokenizers.en.EnglishWordTokenizer;

/**
 * Support for English - use the sub classes {@link BritishEnglish}, {@link AmericanEnglish},
 * etc. if you need spell checking.
 */
public class English extends Language {

  private static final Language AMERICAN_ENGLISH = new AmericanEnglish();

  private Tagger tagger;
  private Chunker chunker;
  private SentenceTokenizer sentenceTokenizer;
  private Synthesizer synthesizer;
  private Disambiguator disambiguator;
  private WordTokenizer wordTokenizer;
  private String name = "English";

  @Override
  public Language getDefaultLanguageVariant() {
    return AMERICAN_ENGLISH;
  }

  @Override
  public final SentenceTokenizer getSentenceTokenizer() {
    if (sentenceTokenizer == null) {
      sentenceTokenizer = new SRXSentenceTokenizer(this);
    }
    return sentenceTokenizer;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public final String getShortName() {
    return "en";
  }

  @Override
  public String[] getCountries() {
    return new String[]{};
  }

  @Override
  public final Tagger getTagger() {
    if (tagger == null) {
      tagger = new EnglishTagger();
    }
    return tagger;
  }

  /**
   * @since 2.3
   */
  @Override
  public Chunker getChunker() {
    if (chunker == null) {
      chunker = new EnglishChunker();
    }
    return chunker;
  }

  @Override
  public final Synthesizer getSynthesizer() {
    if (synthesizer == null) {
      synthesizer = new EnglishSynthesizer();
    }
    return synthesizer;
  }

  @Override
  public final Disambiguator getDisambiguator() {
    if (disambiguator == null) {
      disambiguator = new XmlRuleDisambiguator(new English());
    }
    return disambiguator;
  }

  @Override
  public final WordTokenizer getWordTokenizer() {
    if (wordTokenizer == null) {
      wordTokenizer = new EnglishWordTokenizer();
    }
    return wordTokenizer;
  }

  @Override
  public final Contributor[] getMaintainers() {
    return new Contributor[] { Contributors.MARCIN_MILKOWSKI, Contributors.DANIEL_NABER };
  }

  @Override
  public List<Class<? extends Rule>> getRelevantRules() {
    return Arrays.asList(
        CommaWhitespaceRule.class,
        DoublePunctuationRule.class,
        UppercaseSentenceStartRule.class,
        MultipleWhitespaceRule.class,
        LongSentenceRule.class,
        SentenceWhitespaceRule.class,
        // specific to English:
        EnglishUnpairedBracketsRule.class,
        EnglishWordRepeatRule.class,
        AvsAnRule.class,
        EnglishWordRepeatBeginningRule.class,
        CompoundRule.class,
        ContractionSpellingRule.class
    );
  }

}
