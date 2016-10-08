package edu.csci1933;

/**
 * Created by Noah on 9/26/2015.
 */
public class TweetCounter {

    private String user;
    public double totalUserTweets = 0;
    public int numRetweets = 0;
    public int numRetweeted = 0;
    public double totalTweets;
    public boolean isTopTweeter = false;

    public TweetCounter(String user,double tweetCount) {
        this.user = user;
        this.totalTweets = tweetCount;
    }

    public String toString() {
        String dashTopTweeter = "";
        String topTweeter = "";
        if (isTopTweeter) {
            dashTopTweeter = " - Top Tweeter";
            topTweeter = "Top Tweeter: ";
        }
        return String.format("%s - %.0f - %.0f%% - %d - %d%s\n %s$%.2f Tweets: $%.2f Retweets: $%.2f Retweeted: $%.2f"
                ,user
                ,totalUserTweets
                ,calcPercent()
                ,numRetweets
                ,numRetweeted
                ,dashTopTweeter
                ,topTweeter
                ,getTopTweeterPrize()
                ,getTweetMoney()
                ,getRetweetMoney()
                ,getRetweetedMoney());
    }

    public String getUser() {
        return user;
    }

    public double getTopTweeterPrize() {
        return (totalUserTweets)*.05;
    }

    public double getTweetMoney() {
        return (totalUserTweets - numRetweets)*.1;
    }

    public double getRetweetMoney() {
        return numRetweets*.05;
    }

    public double getRetweetedMoney() {
        return numRetweeted*.25;
    }

    public double getTotalMoney() {
        return getTweetMoney() + getRetweetMoney() + getRetweetedMoney();
    }

    public void incTotalUserTweets() {
        totalUserTweets++;
    }

    public void incNumRetweets() {
        numRetweets++;
    }

    public void incNumRetweeted() {
        numRetweeted++;
    }

    public void setTopTweeter(boolean isTopTweeter) {
        this.isTopTweeter = isTopTweeter;
    }

    private double calcPercent() {
        return Math.round((totalUserTweets/totalTweets)*100);
    }
}
