package edu.csci1933;

/**
 * Created by Noah on 9/25/2015.
 */
public class TweetProcessor {
    private TweetCounter[] users;
    private double totalTweets;
    private String dataLocation = "dat/tweets.dat";
    private TweetCounter topTweeter;
    private double topTweeterCost;

    public TweetProcessor(String[] userList) {
        setTotalTweetCount();
        this.users = new TweetCounter[userList.length];
        for (int i=0; i<userList.length; i++) {
            users[i] = new TweetCounter(userList[i],totalTweets);
        }
    }

    private void setTotalTweetCount() {
        TweetReader reader = new TweetReader(dataLocation);
        while(reader.advance()) {
            totalTweets++;
        }
    }

    private void countTweets() {
        for (TweetCounter user : users) {
            TweetReader reader = new TweetReader(dataLocation);
            while (reader.advance()) {
                String id = reader.getTweeterID();
                String body = reader.getTweet();

                if (id.equals(user.getUser())) {
                    user.incTotalUserTweets();

                    if (body.contains("RT")) {
                        user.incNumRetweets();
                    }
                }

                if (body.substring(0,2).equals("RT")) {
                    body = body.substring(4);
                    int spacePos = body.indexOf(" ");
                    body = body.substring(0,spacePos);

                    if (body.substring(body.length()-1,body.length()).equals(":")) {
                        body = body.substring(0,body.length()-1);
                    }

                    if (user.getUser().equals(body)) {
                        user.incNumRetweeted();
                    }
                }
            }
        }
    }

    private void findTopTweeter() {
        TweetCounter topTweeter = users[0];
        for (TweetCounter user : users) {
            if (user.getTotalMoney() > topTweeter.getTotalMoney()) {
                topTweeter = user;
            }
        }
        topTweeter.setTopTweeter(true);
        this.topTweeter = topTweeter;
        this.topTweeterCost = topTweeter.getTopTweeterPrize();
    }

    private double[] calcOverallCosts() {
        double[] rArray = new double[4];
        rArray[0] = 0; // tweetCost
        rArray[1] = 0; // retweetCost
        rArray[2] = 0; // retweetedCost
        rArray[3] = 0; // totalCost

        for (TweetCounter user : users) {
            rArray[0] += user.getTweetMoney();
            rArray[1] += user.getRetweetMoney();
            rArray[2] += user.getRetweetedMoney();
        }

        rArray[3] = rArray[0] + rArray[1] + rArray[2] + topTweeterCost;
        return rArray;
    }

    private void printOverallCost() {
        double[] costs = calcOverallCosts();
        System.out.println(String.format("Overall cost: $%.2f\n" +
                " Overall cost of original tweets: $%.2f\n" +
                " Overall cost of retweeting others: $%.2f\n" +
                " Overall cost of being retweeted: $%.2f\n" +
                "Cost of Top Tweeter award: $%.2f",costs[3],costs[0],costs[1],costs[2],topTweeterCost));
    }

    private void printData() {
        for (TweetCounter user : users) {
            System.out.println(user);
        }
    }

    public static void main(String[] args) {
        String[] followedUsers = {"lorenterveen","pjrey","katypearce","wyoumans","caseyspruill","pstamara","jvitak","sharoda","MO_GY","barrywellman"};
        TweetProcessor processor = new TweetProcessor(followedUsers);
        processor.countTweets();
        processor.findTopTweeter();
        processor.printOverallCost();
        System.out.println();
        processor.printData();
    }
}