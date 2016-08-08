package com.paymill.services;

import java.util.List;

import com.paymill.models.Client;
import com.paymill.models.Interval;
import com.paymill.models.Offer;
import com.paymill.models.PaymillList;
import com.paymill.utils.HttpClient;
import com.paymill.utils.ParameterMap;

/**
 * The {@link OfferService} is used to list, create, edit, delete and update PAYMILL {@link Offer}s.
 *
 * @author Vassil Nikolov
 * @since 3.0.0
 */
public class OfferService extends AbstractService {

    private OfferService(HttpClient httpClient) {
        super(httpClient);
    }

    private final static String PATH = "/offers";

    /**
     * This function returns a {@link List} of PAYMILL {@link Offer} objects.
     *
     * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Offer}s and their total count.
     */
    public PaymillList<Offer> list() {
        return this.list(null, null, null, null);
    }

    /**
     * This function returns a {@link List} of PAYMILL {@link Offer} objects, overriding the default count and offset.
     *
     * @param count  Max {@link Integer} of returned objects in the {@link PaymillList}
     * @param offset {@link Integer} to start from.
     * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Offer}s and their total count.
     */
    public PaymillList<Offer> list(Integer count, Integer offset) {
        return this.list(null, null, count, offset);
    }

    /**
     * This function returns a {@link List} of PAYMILL {@link Offer} objects.<br>
     * <br>
     * In which order this list is returned depends on the optional parameters. If <code>null</code> is given, no
     * filter or order will be applied.
     *
     * @param filter {@link com.paymill.models.Offer.Filter} or <code>null</code>
     * @param order  {@link com.paymill.models.Offer.Order} or <code>null</code>
     * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Offer}s and their total count.
     */
    public PaymillList<Offer> list(Offer.Filter filter, Offer.Order order) {
        return this.list(filter, order, null, null);
    }

    /**
     * This function returns a {@link List} of PAYMILL {@link Offer} objects.<br>
     * <br>
     * In which order this list is returned depends on the optional parameters. If <code>null</code> is given, no
     * filter or order will be applied, overriding the default count and offset.
     *
     * @param filter {@link com.paymill.models.Offer.Filter} or <code>null</code>
     * @param order  {@link com.paymill.models.Offer.Order} or <code>null</code>
     * @param count  Max {@link Integer} of returned objects in the {@link PaymillList}
     * @param offset {@link Integer} to start from.
     * @return {@link PaymillList} which contains a {@link List} of PAYMILL {@link Offer}s and their total count.
     */
    public PaymillList<Offer> list(Offer.Filter filter, Offer.Order order, Integer count, Integer offset) {
        return RestfulUtils.list(OfferService.PATH, filter, order, count, offset, Offer.class, super.httpClient);
    }

    /**
     * Get and refresh the details of an existing PAYMILL {@link Offer}.
     *
     * @param offer A {@link Offer} with Id.
     * @return Refreshed instance of the given {@link Offer}.
     */
    public Offer get(Offer offer) {
        return RestfulUtils.show(OfferService.PATH, offer, Offer.class, super.httpClient);
    }

    /**
     * Get and refresh the details of an existing PAYMILL {@link Offer}.
     *
     * @param offerId Id of the {@link Offer}
     * @return Refreshed instance of the given {@link Offer}.
     */
    public Offer get(String offerId) {
        return this.get(new Offer(offerId));
    }

    /**
     * Creates an offer via the API.
     *
     * @param amount   Amount in cents &rsaquo; 0.
     * @param currency ISO 4217 formatted currency code.
     * @param interval Defining how often the {@link Client} should be charged. Format: number DAY | WEEK | MONTH | YEAR
     * @param name     Your name for this offer
     * @return {@link Offer} object with id, which represents a PAYMILL offer.
     */
    public Offer create(Integer amount, String currency, Interval.Period interval, String name) {
        return this.create(amount, currency, interval, name, null);
    }

    /**
     * Creates an offer via the API.
     *
     * @param amount          Amount in cents &rsaquo; 0.
     * @param currency        ISO 4217 formatted currency code.
     * @param interval        Defining how often the {@link Client} should be charged.
     * @param name            Your name for this offer
     * @param trialPeriodDays Give it a try or charge directly. Can be <code>null</code>.
     * @return {@link Offer} object with id, which represents a PAYMILL offer.
     */
    public Offer create(Integer amount, String currency, Interval.Period interval, String name, Integer trialPeriodDays) {
        ValidationUtils.validatesAmount(amount);
        ValidationUtils.validatesCurrency(currency);
        ValidationUtils.validatesIntervalPeriod(interval);
        ValidationUtils.validatesName(name);
        ValidationUtils.validatesTrialPeriodDays(trialPeriodDays);

        ParameterMap<String, String> params = new ParameterMap<String, String>();
        params.add("amount", String.valueOf(amount));
        params.add("currency", currency);
        params.add("interval", interval.toString());
        params.add("name", name);
        if (trialPeriodDays != null)
            params.add("trial_period_days", String.valueOf(trialPeriodDays));

        return RestfulUtils.create(OfferService.PATH, params, Offer.class, super.httpClient);
    }

    /**
     * Creates an offer via the API.
     *
     * @param amount   Amount in cents &rsaquo; 0.
     * @param currency ISO 4217 formatted currency code.
     * @param interval Defining how often the {@link Client} should be charged. Format: number DAY | WEEK | MONTH | YEAR
     * @param name     Your name for this offer
     * @return {@link Offer} object with id, which represents a PAYMILL offer.
     */
    public Offer create(Integer amount, String currency, String interval, String name) {
        return this.create(amount, currency, interval, name, null);
    }

    /**
     * Creates an offer via the API.
     *
     * @param amount          Amount in cents &rsaquo; 0.
     * @param currency        ISO 4217 formatted currency code.
     * @param interval        Defining how often the {@link Client} should be charged.
     * @param name            Your name for this offer
     * @param trialPeriodDays Give it a try or charge directly. Can be <code>null</code>.
     * @return {@link Offer} object with id, which represents a PAYMILL offer.
     */
    public Offer create(Integer amount, String currency, String interval, String name, Integer trialPeriodDays) {
        return this.create(amount, currency, new Interval.Period(interval), name, null);
    }

    /**
     * Updates an offer.<br>
     * <br>
     * Following properties will be updated:<br>
     * <ul>
     * <li>name
     * <li>amount
     * <li>interval
     * <li>currency
     * </ul>
     *
     * @param offer               {@link Offer} with Id.
     * @param updateSubscriptions When set to true, all associated subscriptions will be updated (except
     *                            trial_period_days). When set to false, no subscriptions will be updated.
     * @return the updated offer.
     */
    public Offer update(Offer offer, boolean updateSubscriptions) {
        ParameterMap<String, String> params = new ParameterMap<String, String>();
        params.add("update_subscriptions", String.valueOf(updateSubscriptions));
        return RestfulUtils.update(OfferService.PATH, offer, params, true, Offer.class, super.httpClient);
    }

    /**
     * Remove an offer.
     *
     * @param offer                   the {@link Offer}.
     * @param removeWithSubscriptions if true, the plan and all subscriptions associated with it will be deleted. If
     *                                false, only the plan will be deleted.
     */
    public void delete(Offer offer, boolean removeWithSubscriptions) {
        ParameterMap<String, String> params = new ParameterMap<String, String>();
        params.add("remove_with_subscriptions", String.valueOf(removeWithSubscriptions));
        RestfulUtils.delete(OfferService.PATH, offer, params, Offer.class, super.httpClient);
    }

    /**
     * Remove an offer.
     *
     * @param offerId                 Id of the {@link Offer}
     * @param removeWithSubscriptions if true, the plan and all subscriptions associated with it will be deleted. If false, only the plan will be deleted.
     */
    public void delete(String offerId, boolean removeWithSubscriptions) {
        this.delete(new Offer(offerId), removeWithSubscriptions);
    }
}
